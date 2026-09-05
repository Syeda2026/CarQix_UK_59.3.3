package com.aaapp.appguru.myusedcarsaleuk.core.config

object ConfigManager {
    private val localProvider = LocalConfigProvider()
    private var activeProvider: ConfigProvider = localProvider

    fun setProvider(provider: ConfigProvider) {
        activeProvider = provider
    }

    /**
     * All Ad Requests Go Through localProvider:
     * ConfigManager.adsConfig always routes directly to localProvider.getAdsConfig().
     * The app never blocks or waits for network roundtrips to show ads.
     */
    val adsConfig: AdsConfig
        get() = localProvider.getAdsConfig()

    /**
     * Updates the local ads configuration. This is typically called from
     * FirebaseConfigProvider when it receives an update from Remote Config.
     */
    fun updateLocalAdsConfig(config: AdsConfig) {
        localProvider.updateConfig(config)
    }

    val appConfig: AppConfig
        get() = activeProvider.getAppConfig()

    val affiliateConfig: AffiliateConfig
        get() = activeProvider.getAffiliateConfig()

    val featureFlags: FeatureFlags
        get() = activeProvider.getFeatureFlags()

    fun getResolvedUrl(partnerId: String): String {
        val isProd = appConfig.environment == Environment.PRODUCTION
        return activeProvider.getResolvedUrl(partnerId, isProd)
    }

    fun getSearchUrl(
        partnerId: String,
        make: String,
        model: String,
        maxPrice: Int = 0,
        minYear: Int = 0,
        fuelType: String = "",
        transmission: String = ""
    ): String {
        return SearchUrlBuilder.buildSearchUrl(partnerId, make, model, maxPrice, minYear, fuelType, transmission)
    }
}
