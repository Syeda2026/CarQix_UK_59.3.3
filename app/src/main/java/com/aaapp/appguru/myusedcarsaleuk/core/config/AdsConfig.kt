package com.aaapp.appguru.myusedcarsaleuk.core.config

data class AdsConfig(
    val isAdsEnabled: Boolean = true,
    val isBannerAdsEnabled: Boolean = true,
    val isInterstitialAdsEnabled: Boolean = true,
/*
    val bannerAdUnitId: String = "ca-app-pub-3940256099942544/6300978111", // Google Test Banner ID
    val nativeAdUnitId: String = "ca-app-pub-3940256099942544/2247696110", // Google Test Native ID
    val interstitialAdUnitId: String = "ca-app-pub-3940256099942544/1033173712", // Google Test Interstitial ID

 */
    val bannerAdUnitId: String = "/22853158016/pubpower_50630",
    val nativeAdUnitId: String = "/22853158016/pubpower_50630",
    val interstitialAdUnitId: String = "/22853158016/pubpower_30927",
    val interstitialMinClickInterval: Int = 3, // Global / fallback click interval

    // Placement 1: External Marketplace / Chrome Custom Tabs
    val isMarketplaceInterstitialEnabled: Boolean = true,
    val marketplaceInterstitialInterval: Int = 2,

    // Placement 2: Tools, Calculators & Guides Navigation
    val isToolsInterstitialEnabled: Boolean = true,
    val toolsInterstitialInterval: Int = 3,

    // Placement 3: Native Ads per Screen (Independent Toggles)
    val isNativeAdsEnabled: Boolean = true, // Global Native Ad Master Toggle
    val isHomeNativeAdEnabled: Boolean = true,
    val isBuyCarsNativeAdEnabled: Boolean = true,
    val isSmartToolsNativeAdEnabled: Boolean = true,
    val isReviewsGuidesNativeAdEnabled: Boolean = true,
    val isCalculatorDetailNativeAdEnabled: Boolean = true,
    val isArticleDetailNativeAdEnabled: Boolean = true,
    val isBuyingAdviceNativeAdEnabled: Boolean = true,
    val isValuationNativeAdEnabled: Boolean = true,
    val isFinanceNativeAdEnabled: Boolean = true,
    val isInsuranceNativeAdEnabled: Boolean = true,
    val isBreakdownNativeAdEnabled: Boolean = true,
    val isSellCarNativeAdEnabled: Boolean = true,
    val isVehicleHistoryNativeAdEnabled: Boolean = true
)
