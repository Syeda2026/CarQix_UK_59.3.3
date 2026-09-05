package com.aaapp.appguru.myusedcarsaleuk.core.config

interface ConfigProvider {
    fun getAppConfig(): AppConfig
    fun getAdsConfig(): AdsConfig
    fun getAffiliateConfig(): AffiliateConfig
    fun getFeatureFlags(): FeatureFlags
    fun getResolvedUrl(partnerId: String, isProduction: Boolean = false): String
}
