package com.aaapp.appguru.myusedcarsaleuk.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppDatabase
import com.aaapp.appguru.myusedcarsaleuk.data.local.SavedItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomTabFavoriteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Extract deep link URL from various Custom Tab intent fields (priority to live intent.data)
        val incomingDataUri = intent.data?.toString()
        val incomingDataString = intent.dataString
        val extraText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: intent.getStringExtra("android.intent.extra.TEXT")
        val customExtraUrl = intent.getStringExtra(EXTRA_URL)
        val activeFallbackUrl = ChromeTabManager.getActiveUrl()

        val rawUrl = sequenceOf(incomingDataUri, incomingDataString, extraText, customExtraUrl, activeFallbackUrl)
            .firstOrNull { it != null && (it.startsWith("http://") || it.startsWith("https://")) }
            ?: return

        val targetUrl = rawUrl.trim()
        if (targetUrl.isBlank()) return

        // Extract or derive fallback candidate webpage title
        val intentSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT)
            ?: intent.getStringExtra("android.intent.extra.SUBJECT")
            ?: intent.getStringExtra(Intent.EXTRA_TITLE)
        val customExtraTitle = intent.getStringExtra(EXTRA_TITLE)
        val activeFallbackTitle = ChromeTabManager.getActiveTitle()

        val candidateTitle = sequenceOf(intentSubject, customExtraTitle, activeFallbackTitle)
            .firstOrNull { !it.isNullOrBlank() }

        val itemId = "car_fav_" + targetUrl.hashCode()
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val dao = db.appDao()

                val isSaved = dao.isItemSavedDirect(itemId) || dao.isItemSavedByUrlDirect(targetUrl)

                if (isSaved) {
                    // Remove from Favorites
                    dao.deleteSavedItem(itemId)
                    dao.deleteSavedItemByUrl(targetUrl)

                    withContext(Dispatchers.Main) {
                        ChromeTabManager.updateActionButtonState(context, isFilled = false)
                        Toast.makeText(context, "Removed from Saved Items", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Resolve actual vehicle metadata (scrapes OpenGraph og:image & og:title, falls back to make/model gallery)
                    val metadata = VehicleMetadataResolver.resolve(
                        url = targetUrl,
                        fallbackTitle = candidateTitle
                    )

                    // Save to Favorites internal DB with deep link, relevant car title and car image
                    val entity = SavedItemEntity(
                        id = itemId,
                        itemType = "CAR_DEEP_LINK",
                        title = metadata.title,
                        subtitle = targetUrl,
                        detailDataJson = targetUrl,
                        imageUrl = metadata.imageUrl,
                        timestamp = System.currentTimeMillis()
                    )
                    dao.insertSavedItem(entity)

                    withContext(Dispatchers.Main) {
                        ChromeTabManager.updateActionButtonState(context, isFilled = true)
                        Toast.makeText(context, "❤️ Saved: ${metadata.title}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_TOGGLE_FAVORITE = "com.aaapp.appguru.myusedcarsaleuk.ACTION_TOGGLE_FAVORITE"
        const val EXTRA_URL = "extra_target_url"
        const val EXTRA_TITLE = "extra_car_title"
        const val EXTRA_IMAGE_URL = "extra_car_image_url"
    }
}
