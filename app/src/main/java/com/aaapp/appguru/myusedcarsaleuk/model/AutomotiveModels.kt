package com.aaapp.appguru.myusedcarsaleuk.model

data class MarketplaceInfo(
    val id: String,
    val name: String,
    val tagLine: String,
    val description: String,
    val rating: Double,
    val reviewsCount: String,
    val benefits: List<String>,
    val badge: String? = null,
    val logoDrawableRes: Int? = null,
    val webUrlKey: String
)

data class ProviderInfo(
    val id: String,
    val name: String,
    val category: String, // "SELL", "VALUE", "FINANCE", "INSURANCE", "HISTORY", "BREAKDOWN"
    val description: String,
    val rating: Double,
    val reviewsCount: String,
    val keyRateOrFeature: String, // e.g., "8.9% APR", "Free Valuation", "24/7 Patrols"
    val benefits: List<String>,
    val badge: String? = null,
    val partnerKey: String
)

data class GuideArticle(
    val id: String,
    val title: String,
    val summary: String,
    val fullContent: String,
    val category: String, // "Buying Guides", "Reviews", "Finance", "Insurance", "EV & Hybrid"
    val readTimeMinutes: Int,
    val isFeatured: Boolean = false,
    val tag: String = "Guide",
    val datePublished: String = "August 2026"
)

data class CalculatorTypeInfo(
    val id: String,
    val name: String,
    val description: String,
    val iconName: String
)
