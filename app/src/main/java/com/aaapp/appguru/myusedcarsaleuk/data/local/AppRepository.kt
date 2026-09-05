package com.aaapp.appguru.myusedcarsaleuk.data.local

import android.content.Context
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import kotlinx.coroutines.flow.Flow

class AppRepository private constructor(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.appDao()
    val dataStoreManager = DataStoreManager(context)

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppRepository(context).also { INSTANCE = it }
            }
        }
    }

    val allSavedItems: Flow<List<SavedItemEntity>> = dao.getAllSavedItems()
    val recentSearches: Flow<List<RecentSearchEntity>> = dao.getRecentSearches()
    val recentActivities: Flow<List<RecentActivityEntity>> = dao.getRecentActivities()

    suspend fun saveItem(item: SavedItemEntity) = dao.insertSavedItem(item)
    suspend fun deleteSavedItem(id: String) = dao.deleteSavedItem(id)
    suspend fun deleteSavedItemByUrl(url: String) = dao.deleteSavedItemByUrl(url)
    fun isSaved(id: String): Flow<Boolean> = dao.isSaved(id)
    suspend fun isItemSavedDirect(id: String): Boolean = dao.isItemSavedDirect(id)
    suspend fun isItemSavedByUrlDirect(url: String): Boolean = dao.isItemSavedByUrlDirect(url)

    suspend fun saveCarDeepLink(url: String, title: String, imageUrl: String = "") {
        val id = "car_fav_" + url.hashCode()
        val entity = SavedItemEntity(
            id = id,
            itemType = "CAR_DEEP_LINK",
            title = title.ifBlank { "Used Car Listing" },
            subtitle = url,
            detailDataJson = url,
            imageUrl = imageUrl,
            timestamp = System.currentTimeMillis()
        )
        dao.insertSavedItem(entity)
    }

    suspend fun addRecentSearch(query: String, category: String = "GLOBAL") {
        if (query.isNotBlank()) {
            dao.insertRecentSearch(RecentSearchEntity(query = query.trim(), category = category))
        }
    }
    suspend fun clearRecentSearches() = dao.clearRecentSearches()

    suspend fun addRecentActivity(type: String, title: String, subtitle: String, routeOrUrl: String) {
        dao.insertRecentActivity(
            RecentActivityEntity(
                activityType = type,
                title = title,
                subtitle = subtitle,
                routeOrUrl = routeOrUrl
            )
        )
    }
    suspend fun clearRecentActivities() = dao.clearRecentActivities()
}
