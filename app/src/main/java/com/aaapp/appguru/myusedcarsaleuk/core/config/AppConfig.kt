package com.aaapp.appguru.myusedcarsaleuk.core.config

data class AppConfig(
    val appName: String = "CarQix UK",
    val environment: Environment = Environment.DEVELOPMENT,
    val isDebugMode: Boolean = true,
    val appVersion: String = "3.3",
    val supportEmail: String = "support@carqix.com",
    val websiteUrl: String = "https://www.carqix.com",
    val privacyPolicyUrl: String = "https://www.carqix.com/privacy",
    val termsUrl: String = "https://www.carqix.com/terms"
)
