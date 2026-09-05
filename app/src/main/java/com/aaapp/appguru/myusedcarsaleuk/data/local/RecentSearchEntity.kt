package com.aaapp.appguru.myusedcarsaleuk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val query: String,
    val category: String = "GLOBAL",
    val timestamp: Long = System.currentTimeMillis()
)
