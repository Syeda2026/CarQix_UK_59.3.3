package com.aaapp.appguru.myusedcarsaleuk.core.config

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.json.JSONObject

class FirebaseConfigProvider(
    private val localFallback: LocalConfigProvider = LocalConfigProvider(),
    private val onAdsConfigUpdated: ((AdsConfig) -> Unit)? = null,
    private val onConfigUpdated: (() -> Unit)? = null
) : ConfigProvider {

    companion object {
        private const val TAG = "FirebaseConfigProvider"

        // Remote Config Keys (JSON Blobs)
        private const val KEY_ADS_CONFIG_JSON = "ads_config_json"
        private const val KEY_AFFILIATE_CONFIG_JSON = "affiliate_config_json"

        // Remote Config Keys (Individual Parameters)
        private const val KEY_IS_ADS_ENABLED = "is_ads_enabled"
        private const val KEY_IS_BANNER_ADS_ENABLED = "is_banner_ads_enabled"
        private const val KEY_IS_INTERSTITIAL_ADS_ENABLED = "is_interstitial_ads_enabled"
        private const val KEY_IS_MARKETPLACE_INTERSTITIAL_ENABLED = "is_marketplace_interstitial_enabled"
        private const val KEY_MARKETPLACE_INTERSTITIAL_INTERVAL = "marketplace_interstitial_interval"
        private const val KEY_IS_TOOLS_INTERSTITIAL_ENABLED = "is_tools_interstitial_enabled"
        private const val KEY_TOOLS_INTERSTITIAL_INTERVAL = "tools_interstitial_interval"
        private const val KEY_IS_NATIVE_ADS_ENABLED = "is_native_ads_enabled"
        private const val KEY_INTERSTITIAL_INTERVAL = "interstitial_interval"
        private const val KEY_BANNER_AD_UNIT_ID = "banner_ad_unit_id"
        private const val KEY_INTERSTITIAL_AD_UNIT_ID = "interstitial_ad_unit_id"
        private const val KEY_NATIVE_AD_UNIT_ID = "native_ad_unit_id"

        // Screen-level Native Ad Keys
        private const val KEY_IS_HOME_NATIVE_AD_ENABLED = "is_home_native_ad_enabled"
        private const val KEY_IS_BUY_CARS_NATIVE_AD_ENABLED = "is_buy_cars_native_ad_enabled"
        private const val KEY_IS_SELL_CAR_NATIVE_AD_ENABLED = "is_sell_car_native_ad_enabled"
        private const val KEY_IS_VALUATION_NATIVE_AD_ENABLED = "is_valuation_native_ad_enabled"
        private const val KEY_IS_VEHICLE_HISTORY_NATIVE_AD_ENABLED = "is_vehicle_history_native_ad_enabled"
        private const val KEY_IS_FINANCE_NATIVE_AD_ENABLED = "is_finance_native_ad_enabled"
        private const val KEY_IS_INSURANCE_NATIVE_AD_ENABLED = "is_insurance_native_ad_enabled"
        private const val KEY_IS_BREAKDOWN_NATIVE_AD_ENABLED = "is_breakdown_native_ad_enabled"
        private const val KEY_IS_SMART_TOOLS_NATIVE_AD_ENABLED = "is_smart_tools_native_ad_enabled"
        private const val KEY_IS_REVIEWS_GUIDES_NATIVE_AD_ENABLED = "is_reviews_guides_native_ad_enabled"
    }

    private var remoteConfig: FirebaseRemoteConfig? = null
    private var cachedAdsConfig: AdsConfig = localFallback.getAdsConfig()
    private var cachedAffiliateConfig: AffiliateConfig = localFallback.getAffiliateConfig()

    init {
        try {
            val config = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0) // Fetch latest values immediately for responsive updates
                .build()
            config.setConfigSettingsAsync(configSettings)

            // Setup default parameters matching local fallback
            val defaults = HashMap<String, Any>()
            val defaultAds = localFallback.getAdsConfig()
            defaults[KEY_IS_ADS_ENABLED] = defaultAds.isAdsEnabled
            defaults[KEY_IS_BANNER_ADS_ENABLED] = defaultAds.isBannerAdsEnabled
            defaults[KEY_IS_INTERSTITIAL_ADS_ENABLED] = defaultAds.isInterstitialAdsEnabled
            defaults[KEY_IS_MARKETPLACE_INTERSTITIAL_ENABLED] = defaultAds.isMarketplaceInterstitialEnabled
            defaults[KEY_MARKETPLACE_INTERSTITIAL_INTERVAL] = defaultAds.marketplaceInterstitialInterval
            defaults[KEY_IS_TOOLS_INTERSTITIAL_ENABLED] = defaultAds.isToolsInterstitialEnabled
            defaults[KEY_TOOLS_INTERSTITIAL_INTERVAL] = defaultAds.toolsInterstitialInterval
            defaults[KEY_IS_NATIVE_ADS_ENABLED] = defaultAds.isNativeAdsEnabled
            defaults[KEY_INTERSTITIAL_INTERVAL] = defaultAds.interstitialMinClickInterval
            defaults[KEY_BANNER_AD_UNIT_ID] = defaultAds.bannerAdUnitId
            defaults[KEY_INTERSTITIAL_AD_UNIT_ID] = defaultAds.interstitialAdUnitId
            defaults[KEY_NATIVE_AD_UNIT_ID] = defaultAds.nativeAdUnitId
            defaults[KEY_IS_HOME_NATIVE_AD_ENABLED] = defaultAds.isHomeNativeAdEnabled
            defaults[KEY_IS_BUY_CARS_NATIVE_AD_ENABLED] = defaultAds.isBuyCarsNativeAdEnabled
            defaults[KEY_IS_SELL_CAR_NATIVE_AD_ENABLED] = defaultAds.isSellCarNativeAdEnabled
            defaults[KEY_IS_VALUATION_NATIVE_AD_ENABLED] = defaultAds.isValuationNativeAdEnabled
            defaults[KEY_IS_VEHICLE_HISTORY_NATIVE_AD_ENABLED] = defaultAds.isVehicleHistoryNativeAdEnabled
            defaults[KEY_IS_FINANCE_NATIVE_AD_ENABLED] = defaultAds.isFinanceNativeAdEnabled
            defaults[KEY_IS_INSURANCE_NATIVE_AD_ENABLED] = defaultAds.isInsuranceNativeAdEnabled
            defaults[KEY_IS_BREAKDOWN_NATIVE_AD_ENABLED] = defaultAds.isBreakdownNativeAdEnabled
            defaults[KEY_IS_SMART_TOOLS_NATIVE_AD_ENABLED] = defaultAds.isSmartToolsNativeAdEnabled
            defaults[KEY_IS_REVIEWS_GUIDES_NATIVE_AD_ENABLED] = defaultAds.isReviewsGuidesNativeAdEnabled

            config.setDefaultsAsync(defaults)
            remoteConfig = config

            // Fetch and activate on startup
            fetchAndApply()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Firebase Remote Config", e)
        }
    }

    fun fetchAndApply(onComplete: ((Boolean) -> Unit)? = null) {
        val config = remoteConfig ?: return
        config.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Remote Config fetch succeeded. Activated new params: $updated")
                    refreshConfigs()
                    onComplete?.invoke(true)
                } else {
                    Log.w(TAG, "Remote Config fetch failed", task.exception)
                    onComplete?.invoke(false)
                }
            }
    }

    private fun refreshConfigs() {
        val config = remoteConfig ?: return

        // 1. Refresh Ads Config
        val adsJson = config.getString(KEY_ADS_CONFIG_JSON)
        if (adsJson.isNotBlank() && adsJson != "{}") {
            cachedAdsConfig = parseAdsJson(adsJson)
        } else {
            // Fall back to individual keys
            val fallback = localFallback.getAdsConfig()
            val globalInterstitial = config.getBoolean(KEY_IS_INTERSTITIAL_ADS_ENABLED)
            val globalInterval = config.getLong(KEY_INTERSTITIAL_INTERVAL).toInt().coerceAtLeast(1)

            val mktpEnabled = if (config.getString(KEY_IS_MARKETPLACE_INTERSTITIAL_ENABLED).isNotBlank()) {
                config.getBoolean(KEY_IS_MARKETPLACE_INTERSTITIAL_ENABLED)
            } else globalInterstitial

            val toolsEnabled = if (config.getString(KEY_IS_TOOLS_INTERSTITIAL_ENABLED).isNotBlank()) {
                config.getBoolean(KEY_IS_TOOLS_INTERSTITIAL_ENABLED)
            } else globalInterstitial

            val mktpInterval = if (config.getString(KEY_MARKETPLACE_INTERSTITIAL_INTERVAL).isNotBlank()) {
                config.getLong(KEY_MARKETPLACE_INTERSTITIAL_INTERVAL).toInt().coerceAtLeast(1)
            } else globalInterval

            val toolsInterval = if (config.getString(KEY_TOOLS_INTERSTITIAL_INTERVAL).isNotBlank()) {
                config.getLong(KEY_TOOLS_INTERSTITIAL_INTERVAL).toInt().coerceAtLeast(1)
            } else globalInterval

            cachedAdsConfig = fallback.copy(
                isAdsEnabled = config.getBoolean(KEY_IS_ADS_ENABLED),
                isBannerAdsEnabled = config.getBoolean(KEY_IS_BANNER_ADS_ENABLED),
                isInterstitialAdsEnabled = globalInterstitial,
                isNativeAdsEnabled = config.getBoolean(KEY_IS_NATIVE_ADS_ENABLED),
                interstitialMinClickInterval = globalInterval,
                isMarketplaceInterstitialEnabled = mktpEnabled,
                marketplaceInterstitialInterval = mktpInterval,
                isToolsInterstitialEnabled = toolsEnabled,
                toolsInterstitialInterval = toolsInterval,
                bannerAdUnitId = config.getString(KEY_BANNER_AD_UNIT_ID).ifBlank { fallback.bannerAdUnitId },
                interstitialAdUnitId = config.getString(KEY_INTERSTITIAL_AD_UNIT_ID).ifBlank { fallback.interstitialAdUnitId },
                nativeAdUnitId = config.getString(KEY_NATIVE_AD_UNIT_ID).ifBlank { fallback.nativeAdUnitId },
                isHomeNativeAdEnabled = config.getBoolean(KEY_IS_HOME_NATIVE_AD_ENABLED),
                isBuyCarsNativeAdEnabled = config.getBoolean(KEY_IS_BUY_CARS_NATIVE_AD_ENABLED),
                isSellCarNativeAdEnabled = config.getBoolean(KEY_IS_SELL_CAR_NATIVE_AD_ENABLED),
                isValuationNativeAdEnabled = config.getBoolean(KEY_IS_VALUATION_NATIVE_AD_ENABLED),
                isVehicleHistoryNativeAdEnabled = config.getBoolean(KEY_IS_VEHICLE_HISTORY_NATIVE_AD_ENABLED),
                isFinanceNativeAdEnabled = config.getBoolean(KEY_IS_FINANCE_NATIVE_AD_ENABLED),
                isInsuranceNativeAdEnabled = config.getBoolean(KEY_IS_INSURANCE_NATIVE_AD_ENABLED),
                isBreakdownNativeAdEnabled = config.getBoolean(KEY_IS_BREAKDOWN_NATIVE_AD_ENABLED),
                isSmartToolsNativeAdEnabled = config.getBoolean(KEY_IS_SMART_TOOLS_NATIVE_AD_ENABLED),
                isReviewsGuidesNativeAdEnabled = config.getBoolean(KEY_IS_REVIEWS_GUIDES_NATIVE_AD_ENABLED)
            )
        }

        // Notify specific ads listener if new config was loaded
        onAdsConfigUpdated?.invoke(cachedAdsConfig)

        // 2. Refresh Affiliate Config
        val affiliateJson = config.getString(KEY_AFFILIATE_CONFIG_JSON)
        if (affiliateJson.isNotBlank() && affiliateJson != "{}") {
            cachedAffiliateConfig = parseAffiliateJson(affiliateJson)
        }

        // Notify listeners that new config has been applied
        try {
            onConfigUpdated?.invoke()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onConfigUpdated callback", e)
        }
    }

    private fun parseAdsJson(jsonStr: String): AdsConfig {
        return try {
            val json = JSONObject(jsonStr)
            val base = localFallback.getAdsConfig()
            val globalInterstitial = json.optBoolean("isInterstitialAdsEnabled", base.isInterstitialAdsEnabled)
            val globalInterval = json.optInt("interstitialInterval", base.interstitialMinClickInterval)

            base.copy(
                isAdsEnabled = json.optBoolean("isAdsEnabled", base.isAdsEnabled),
                isBannerAdsEnabled = json.optBoolean("isBannerAdsEnabled", base.isBannerAdsEnabled),
                isInterstitialAdsEnabled = globalInterstitial,
                interstitialMinClickInterval = globalInterval,

                // Placement 1: External Marketplace / Chrome Custom Tabs
                isMarketplaceInterstitialEnabled = json.optBoolean("isMarketplaceInterstitialEnabled", globalInterstitial),
                marketplaceInterstitialInterval = json.optInt("marketplaceInterstitialInterval", globalInterval),

                // Placement 2: Tools, Calculators & Guides Navigation
                isToolsInterstitialEnabled = json.optBoolean("isToolsInterstitialEnabled", globalInterstitial),
                toolsInterstitialInterval = json.optInt("toolsInterstitialInterval", globalInterval),

                // Placement 3: Native Ads
                isNativeAdsEnabled = json.optBoolean("isNativeAdsEnabled", base.isNativeAdsEnabled),
                bannerAdUnitId = json.optString("bannerAdUnitId", base.bannerAdUnitId),
                interstitialAdUnitId = json.optString("interstitialAdUnitId", base.interstitialAdUnitId),
                nativeAdUnitId = json.optString("nativeAdUnitId", base.nativeAdUnitId),
                isHomeNativeAdEnabled = json.optBoolean("isHomeNativeAdEnabled", base.isHomeNativeAdEnabled),
                isBuyCarsNativeAdEnabled = json.optBoolean("isBuyCarsNativeAdEnabled", base.isBuyCarsNativeAdEnabled),
                isSmartToolsNativeAdEnabled = json.optBoolean("isSmartToolsNativeAdEnabled", base.isSmartToolsNativeAdEnabled),
                isReviewsGuidesNativeAdEnabled = json.optBoolean("isReviewsGuidesNativeAdEnabled", base.isReviewsGuidesNativeAdEnabled),
                isCalculatorDetailNativeAdEnabled = json.optBoolean("isCalculatorDetailNativeAdEnabled", base.isCalculatorDetailNativeAdEnabled),
                isArticleDetailNativeAdEnabled = json.optBoolean("isArticleDetailNativeAdEnabled", base.isArticleDetailNativeAdEnabled),
                isBuyingAdviceNativeAdEnabled = json.optBoolean("isBuyingAdviceNativeAdEnabled", base.isBuyingAdviceNativeAdEnabled),
                isValuationNativeAdEnabled = json.optBoolean("isValuationNativeAdEnabled", base.isValuationNativeAdEnabled),
                isFinanceNativeAdEnabled = json.optBoolean("isFinanceNativeAdEnabled", base.isFinanceNativeAdEnabled),
                isInsuranceNativeAdEnabled = json.optBoolean("isInsuranceNativeAdEnabled", base.isInsuranceNativeAdEnabled),
                isBreakdownNativeAdEnabled = json.optBoolean("isBreakdownNativeAdEnabled", base.isBreakdownNativeAdEnabled),
                isSellCarNativeAdEnabled = json.optBoolean("isSellCarNativeAdEnabled", base.isSellCarNativeAdEnabled),
                isVehicleHistoryNativeAdEnabled = json.optBoolean("isVehicleHistoryNativeAdEnabled", base.isVehicleHistoryNativeAdEnabled)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing Ads JSON from Remote Config", e)
            localFallback.getAdsConfig()
        }
    }

    private fun parseAffiliateJson(jsonStr: String): AffiliateConfig {
        return try {
            val json = JSONObject(jsonStr)
            val currentMap = localFallback.getAffiliateConfig().partnerUrls.toMutableMap()
            val customTag = json.optString("affiliateTag", "")

            if (customTag.isNotBlank()) {
                currentMap.forEach { (key, partner) ->
                    val updatedAffiliateUrl = if (partner.affiliateUrl.contains("aff=")) {
                        partner.affiliateUrl.replace(Regex("aff=[^&]+"), "aff=$customTag")
                    } else {
                        partner.affiliateUrl
                    }
                    currentMap[key] = partner.copy(affiliateUrl = updatedAffiliateUrl)
                }
            }
            AffiliateConfig(partnerUrls = currentMap)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing Affiliate JSON from Remote Config", e)
            localFallback.getAffiliateConfig()
        }
    }

    override fun getAppConfig(): AppConfig = localFallback.getAppConfig().copy(
        environment = Environment.PRODUCTION
    )

    override fun getAdsConfig(): AdsConfig = cachedAdsConfig

    override fun getAffiliateConfig(): AffiliateConfig = cachedAffiliateConfig

    override fun getFeatureFlags(): FeatureFlags = localFallback.getFeatureFlags()

    override fun getResolvedUrl(partnerId: String, isProduction: Boolean): String {
        val partner = cachedAffiliateConfig.partnerUrls[partnerId]
            ?: return localFallback.getResolvedUrl(partnerId, isProduction)

        return if (isProduction && partner.isEnabled) {
            partner.affiliateUrl
        } else {
            partner.publicUrl
        }
    }
}
