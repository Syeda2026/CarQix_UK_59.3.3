package com.aaapp.appguru.myusedcarsaleuk.core.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdManager {

    private const val TAG = "AdManager"
    private var isInitialized = false
    private var interstitialAd: InterstitialAd? = null
    private var loadedAdUnitId: String? = null
    private var isLoadingInterstitial = false
    private var marketplaceActionCounter = 0
    private var toolsActionCounter = 0
    var isShowingInterstitial: Boolean = false
        private set

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) { initializationStatus ->
                Log.d(TAG, "AdMob MobileAds initialized: ${initializationStatus.adapterStatusMap}")
                isInitialized = true
                preloadInterstitial(context.applicationContext)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize MobileAds", e)
        }
    }

    /**
     * Called whenever Firebase Remote Config successfully fetches and activates new parameters.
     */
    fun onConfigUpdated(context: Context) {
        val adsConfig = ConfigManager.adsConfig
        Log.d(TAG, "AdManager onConfigUpdated: isAdsEnabled=${adsConfig.isAdsEnabled}, interstitialId=${adsConfig.interstitialAdUnitId}, mktpInterval=${adsConfig.marketplaceInterstitialInterval}, toolsInterval=${adsConfig.toolsInterstitialInterval}")

        if (!adsConfig.isAdsEnabled || !adsConfig.isInterstitialAdsEnabled) {
            interstitialAd = null
            loadedAdUnitId = null
            return
        }

        // If the configured ad unit changed (e.g. from test ID to Firebase production ID), discard old ad and reload
        if (loadedAdUnitId != null && loadedAdUnitId != adsConfig.interstitialAdUnitId) {
            Log.d(TAG, "Interstitial Unit ID changed from '$loadedAdUnitId' to '${adsConfig.interstitialAdUnitId}'. Replacing preloaded ad.")
            interstitialAd = null
            loadedAdUnitId = null
            preloadInterstitial(context.applicationContext)
        } else if (interstitialAd == null && !isLoadingInterstitial) {
            preloadInterstitial(context.applicationContext)
        }
    }

    fun preloadInterstitial(context: Context) {
        val adsConfig = ConfigManager.adsConfig
        if (!adsConfig.isAdsEnabled || !adsConfig.isInterstitialAdsEnabled) return

        val targetAdUnitId = adsConfig.interstitialAdUnitId.ifBlank { "" }

        // If already loaded with the current active ad unit ID, skip
        if (interstitialAd != null && loadedAdUnitId == targetAdUnitId) return
        if (isLoadingInterstitial) return

        isLoadingInterstitial = true
        val adRequest = AdRequest.Builder().build()

        Log.d(TAG, "Loading interstitial ad for unit: $targetAdUnitId")
        InterstitialAd.load(
            context,
            targetAdUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Interstitial ad loaded successfully for unit: $targetAdUnitId")
                    interstitialAd = ad
                    loadedAdUnitId = targetAdUnitId
                    isLoadingInterstitial = false
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "Interstitial ad failed to load (unit: $targetAdUnitId): code=${loadAdError.code}, message=${loadAdError.message}")
                    interstitialAd = null
                    loadedAdUnitId = null
                    isLoadingInterstitial = false
                }
            }
        )
    }

    /**
     * Placement 1: Triggered when opening external marketplace links / Chrome Custom Tabs
     */
    fun showMarketplaceInterstitialAd(context: Context, onAdDismissed: () -> Unit) {
        val adsConfig = ConfigManager.adsConfig
        if (!adsConfig.isAdsEnabled || !adsConfig.isMarketplaceInterstitialEnabled) {
            onAdDismissed()
            return
        }

        marketplaceActionCounter++
        val minInterval = adsConfig.marketplaceInterstitialInterval.coerceAtLeast(1)
        displayInterstitialIfReady(context, marketplaceActionCounter, minInterval, onAdDismissed)
    }

    /**
     * Placement 2: Triggered when navigating into Tools, Calculators, and Buying Guides
     */
    fun showToolsInterstitialAd(context: Context, onAdDismissed: () -> Unit) {
        val adsConfig = ConfigManager.adsConfig
        if (!adsConfig.isAdsEnabled || !adsConfig.isToolsInterstitialEnabled) {
            onAdDismissed()
            return
        }

        toolsActionCounter++
        val minInterval = adsConfig.toolsInterstitialInterval.coerceAtLeast(1)
        displayInterstitialIfReady(context, toolsActionCounter, minInterval, onAdDismissed)
    }

    /**
     * Legacy / Alias for backward compatibility
     */
    fun showInterstitialAdIfAppropriate(context: Context, onAdDismissed: () -> Unit) {
        showMarketplaceInterstitialAd(context, onAdDismissed)
    }

    private fun displayInterstitialIfReady(
        context: Context,
        counter: Int,
        interval: Int,
        onAdDismissed: () -> Unit
    ) {
        val activity = findActivity(context)
        val ad = interstitialAd

        if (activity != null && ad != null && counter % interval == 0) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad dismissed.")
                    isShowingInterstitial = false
                    interstitialAd = null
                    preloadInterstitial(context.applicationContext)
                    onAdDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.w(TAG, "Interstitial ad failed to show: ${adError.message}")
                    isShowingInterstitial = false
                    interstitialAd = null
                    preloadInterstitial(context.applicationContext)
                    onAdDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad showed full screen.")
                    isShowingInterstitial = true
                }
            }
            ad.show(activity)
        } else {
            // Ad not ready or counter interval not reached
            if (interstitialAd == null) {
                preloadInterstitial(context.applicationContext)
            }
            onAdDismissed()
        }
    }

    private fun findActivity(context: Context): Activity? {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is Activity) {
                return currentContext
            }
            currentContext = currentContext.baseContext
        }
        return null
    }
}
