package com.aaapp.appguru.myusedcarsaleuk.core.config

class LocalConfigProvider : ConfigProvider {
    private val appConfig = AppConfig()
    private var adsConfig = AdsConfig()
    private val affiliateConfig = AffiliateConfig()
    private val featureFlags = FeatureFlags()

    override fun getAppConfig(): AppConfig = appConfig

    override fun getAdsConfig(): AdsConfig = adsConfig

    fun updateConfig(newConfig: AdsConfig) {
        adsConfig = newConfig
    }

    override fun getAffiliateConfig(): AffiliateConfig = affiliateConfig

    override fun getFeatureFlags(): FeatureFlags = featureFlags

    override fun getResolvedUrl(partnerId: String, isProduction: Boolean): String {
        val partner = affiliateConfig.partnerUrls[partnerId]
            ?: return "https://www.google.co.uk/search?q=$partnerId+car+services"
        return if (isProduction) partner.affiliateUrl else partner.publicUrl
    }
}
