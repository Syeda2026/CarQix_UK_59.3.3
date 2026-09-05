package com.aaapp.appguru.myusedcarsaleuk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_activities")
data class RecentActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val activityType: String, // "VIEW_ARTICLE", "VIEW_PROVIDER", "RUN_CALCULATOR", "PERFORM_SEARCH"
    val title: String,
    val subtitle: String,
    val routeOrUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)
