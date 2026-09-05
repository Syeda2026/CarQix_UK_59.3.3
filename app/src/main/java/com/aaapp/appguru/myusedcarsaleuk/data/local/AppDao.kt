package com.aaapp.appguru.myusedcarsaleuk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Saved Items
    @Query("SELECT * FROM saved_items ORDER BY timestamp DESC")
    fun getAllSavedItems(): Flow<List<SavedItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedItem(item: SavedItemEntity)

    @Query("DELETE FROM saved_items WHERE id = :id")
    suspend fun deleteSavedItem(id: String)

    @Query("DELETE FROM saved_items WHERE subtitle = :url OR detailDataJson = :url")
    suspend fun deleteSavedItemByUrl(url: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_items WHERE id = :id)")
    fun isSaved(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_items WHERE id = :id)")
    suspend fun isItemSavedDirect(id: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM saved_items WHERE subtitle = :url OR detailDataJson = :url)")
    suspend fun isItemSavedByUrlDirect(url: String): Boolean

    @Query("SELECT * FROM saved_items WHERE id = :id LIMIT 1")
    suspend fun getSavedItemById(id: String): SavedItemEntity?

    @Query("SELECT * FROM saved_items WHERE subtitle = :url OR detailDataJson = :url LIMIT 1")
    suspend fun getSavedItemByUrl(url: String): SavedItemEntity?

    // Recent Searches
    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC LIMIT 20")
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(search: RecentSearchEntity)

    @Query("DELETE FROM recent_searches")
    suspend fun clearRecentSearches()

    // Recent Activity
    @Query("SELECT * FROM recent_activities ORDER BY timestamp DESC LIMIT 10")
    fun getRecentActivities(): Flow<List<RecentActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentActivity(activity: RecentActivityEntity)

    @Query("DELETE FROM recent_activities")
    suspend fun clearRecentActivities()
}
