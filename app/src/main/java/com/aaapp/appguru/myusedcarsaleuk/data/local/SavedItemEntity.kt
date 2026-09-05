package com.aaapp.appguru.myusedcarsaleuk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_items")
data class SavedItemEntity(
    @PrimaryKey val id: String, // unique string ID, e.g. "provider_autotrader" or "article_1" or "car_fav_..."
    val itemType: String, // "SEARCH", "PROVIDER", "ARTICLE", "CALCULATION", "CAR_DEEP_LINK"
    val title: String,
    val subtitle: String, // URL, note, or secondary description
    val detailDataJson: String = "", // metadata / full deep link URL
    val imageUrl: String = "", // car image or provider logo URL
    val timestamp: Long = System.currentTimeMillis()
)
