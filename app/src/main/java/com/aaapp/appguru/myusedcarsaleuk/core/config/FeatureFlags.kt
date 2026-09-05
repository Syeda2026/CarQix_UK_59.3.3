package com.aaapp.appguru.myusedcarsaleuk.core.config

data class FeatureFlags(
    val isBuyCarsModuleEnabled: Boolean = true,
    val isSellCarModuleEnabled: Boolean = true,
    val isValuationModuleEnabled: Boolean = true,
    val isFinanceModuleEnabled: Boolean = true,
    val isInsuranceModuleEnabled: Boolean = true,
    val isVehicleHistoryModuleEnabled: Boolean = true,
    val isBreakdownModuleEnabled: Boolean = true,
    val isReviewsModuleEnabled: Boolean = true,
    val isSmartToolsModuleEnabled: Boolean = true,
    val isSavedModuleEnabled: Boolean = true,
    val isGlobalSearchEnabled: Boolean = true,
    val isBannerAdsEnabled: Boolean = true,
    val isNativeAdsEnabled: Boolean = true,
    val isInterstitialAdsEnabled: Boolean = true,
    val isDarkThemeSupported: Boolean = true
)
