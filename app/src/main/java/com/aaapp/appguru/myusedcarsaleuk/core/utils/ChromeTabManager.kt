package com.aaapp.appguru.myusedcarsaleuk.core.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.compose.ui.graphics.toArgb
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdManager
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppDatabase
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.NavyDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ChromeTabManager {
    private var customTabsClient: CustomTabsClient? = null
    private var currentSession: CustomTabsSession? = null
    private var isBound: Boolean = false

    private var activeUrl: String = ""
    private var activeTitle: String = ""
    private var activeImageUrl: String = ""

    data class OpeningTabState(
        val title: String,
        val url: String
    )

    val openingTabState = kotlinx.coroutines.flow.MutableStateFlow<OpeningTabState?>(null)

    fun getActiveUrl(): String = activeUrl
    fun getActiveTitle(): String = activeTitle
    fun getActiveImageUrl(): String = activeImageUrl

    private var activeLoadingCallback: ((Boolean) -> Unit)? = null
    private var isLifecycleRegistered: Boolean = false
    private var isTabLaunched: Boolean = false

    /**
     * Initializes and warms up Custom Tabs service connection.
     */
    fun init(context: Context) {
        if (!isLifecycleRegistered) {
            val app = context.applicationContext as? android.app.Application
            app?.registerActivityLifecycleCallbacks(object : android.app.Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: android.os.Bundle?) {}
                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {
                    // Only dismiss if the Custom Tab was already launched and user returned to the app.
                    // If an interstitial ad just closed, do not dismiss so the progress view stays visible!
                    if (isTabLaunched) {
                        dismissLoading()
                    }
                }
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: android.os.Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {
                    if (isTabLaunched) {
                        dismissLoading()
                    }
                }
            })
            isLifecycleRegistered = true
        }

        if (isBound) return
        try {
            val packageName = CustomTabsClient.getPackageName(context, null) ?: return
            isBound = CustomTabsClient.bindCustomTabsService(
                context.applicationContext,
                packageName,
                object : CustomTabsServiceConnection() {
                    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
                        customTabsClient = client
                        client.warmup(0L)
                        currentSession = client.newSession(createSessionCallback())
                    }

                    override fun onServiceDisconnected(name: ComponentName) {
                        customTabsClient = null
                        currentSession = null
                        isBound = false
                        dismissLoading()
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createSessionCallback(): CustomTabsCallback {
        return object : CustomTabsCallback() {
            override fun onNavigationEvent(navigationEvent: Int, extras: android.os.Bundle?) {
                when (navigationEvent) {
                    TAB_SHOWN -> {
                        // Custom Tab is visibly shown to the user on screen
                        Handler(Looper.getMainLooper()).postDelayed({
                            dismissLoading()
                        }, 400)
                    }
                }
            }
        }
    }

    fun dismissLoading() {
        isTabLaunched = false
        if (openingTabState.value != null || activeLoadingCallback != null) {
            Handler(Looper.getMainLooper()).post {
                openingTabState.value = null
                activeLoadingCallback?.invoke(false)
                activeLoadingCallback = null
            }
        }
    }

    /**
     * Creates a crisp 24dp heart bitmap matching the app's bottom navigation bar Saved icon.
     * @param isFilled If true, returns a solid filled heart (Icons.Filled.Favorite).
     *                 If false, returns an outlined empty heart (Icons.Outlined.FavoriteBorder).
     */
    fun createSavedIconBitmap(context: Context, isFilled: Boolean): Bitmap {
        val density = context.resources.displayMetrics.density
        val sizePx = (24 * density).toInt().coerceAtLeast(48)
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.BLACK
            style = if (isFilled) Paint.Style.FILL_AND_STROKE else Paint.Style.STROKE
            strokeWidth = 2.0f * density
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        val scale = sizePx / 24f
        val path: Path = try {
            val p = androidx.core.graphics.PathParser.createPathFromPathData(
                "M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
            )
            val matrix = android.graphics.Matrix().apply {
                setScale(scale, scale)
            }
            p.transform(matrix)
            p
        } catch (e: Exception) {
            Path().apply {
                val cx = sizePx / 2f
                val cy = sizePx / 2f
                moveTo(cx, cy + sizePx * 0.35f)
                cubicTo(
                    cx - sizePx * 0.45f, cy + sizePx * 0.1f,
                    cx - sizePx * 0.45f, cy - sizePx * 0.3f,
                    cx - sizePx * 0.2f, cy - sizePx * 0.3f
                )
                cubicTo(
                    cx - sizePx * 0.05f, cy - sizePx * 0.3f,
                    cx, cy - sizePx * 0.15f,
                    cx, cy - sizePx * 0.15f
                )
                cubicTo(
                    cx, cy - sizePx * 0.15f,
                    cx + sizePx * 0.05f, cy - sizePx * 0.3f,
                    cx + sizePx * 0.2f, cy - sizePx * 0.3f
                )
                cubicTo(
                    cx + sizePx * 0.45f, cy - sizePx * 0.3f,
                    cx + sizePx * 0.45f, cy + sizePx * 0.1f,
                    cx, cy + sizePx * 0.35f
                )
                close()
            }
        }

        canvas.drawPath(path, paint)
        return bitmap
    }

    /**
     * Backward-compatible alias.
     */
    fun createStarBitmap(context: Context, isFilled: Boolean): Bitmap = createSavedIconBitmap(context, isFilled)

    /**
     * Updates the action button on the active Custom Tab toolbar.
     */
    fun updateActionButtonState(context: Context, isFilled: Boolean) {
        try {
            val bitmap = createSavedIconBitmap(context, isFilled)
            val desc = if (isFilled) "Saved in Favorites" else "Save to Favorites"
            currentSession?.setActionButton(bitmap, desc)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun findActivity(context: Context): Activity? {
        var current = context
        while (current is ContextWrapper) {
            if (current is Activity) return current
            current = current.baseContext
        }
        return null
    }

    /**
     * Launches a URL in a Custom Tab equipped with a Favorites Star action button
     * immediately with zero delay.
     */
    fun launchUrl(
        context: Context,
        url: String,
        title: String = "UK Used Car Listing",
        imageUrl: String = "",
        onLoadingStateChange: ((Boolean) -> Unit)? = null
    ) {
        activeUrl = url
        activeTitle = title.ifBlank { "Used Car Listing" }
        activeImageUrl = imageUrl
        isTabLaunched = false
        activeLoadingCallback = onLoadingStateChange

        // Trigger loading state immediately
        onLoadingStateChange?.invoke(true)

        AdManager.showMarketplaceInterstitialAd(context) {
            try {
                // Ensure session is prepared
                if (currentSession == null && customTabsClient != null) {
                    currentSession = customTabsClient?.newSession(createSessionCallback())
                }

                val builder = if (currentSession != null) {
                    CustomTabsIntent.Builder(currentSession)
                } else {
                    CustomTabsIntent.Builder()
                }

                // Toolbar Color - Set to White to match app's top action bar
                val whiteColor = android.graphics.Color.WHITE
                val colorParams = CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(whiteColor)
                    .setSecondaryToolbarColor(whiteColor)
                    .setNavigationBarColor(whiteColor)
                    .build()
                builder.setDefaultColorSchemeParams(colorParams)
                builder.setShowTitle(true)
                builder.setUrlBarHidingEnabled(false)

                // Create PendingIntent for the Favorites Heart Action Button
                val starIntent = Intent(context, CustomTabFavoriteReceiver::class.java).apply {
                    action = CustomTabFavoriteReceiver.ACTION_TOGGLE_FAVORITE
                    putExtra(CustomTabFavoriteReceiver.EXTRA_URL, url)
                    putExtra(CustomTabFavoriteReceiver.EXTRA_TITLE, activeTitle)
                    putExtra(CustomTabFavoriteReceiver.EXTRA_IMAGE_URL, activeImageUrl)
                    setPackage(context.packageName)
                }

                val flags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context.applicationContext,
                    url.hashCode(),
                    starIntent,
                    flags
                )

                // Set Action Button on Custom Tab: Heart Icon matching bottom navigation bar Saved tab
                val savedIconBitmap = createSavedIconBitmap(context, false)
                builder.setActionButton(savedIconBitmap, "Save to Favorites", pendingIntent, false)

                // Add Menu Item for Favorites as well
                builder.addMenuItem("🤍 Save to Favorites", pendingIntent)

                val customTabsIntent = builder.build()
                val activityContext = findActivity(context)

                isTabLaunched = true
                if (activityContext != null) {
                    customTabsIntent.launchUrl(activityContext, Uri.parse(url))
                } else {
                    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    customTabsIntent.launchUrl(context, Uri.parse(url))
                }
            } catch (e: Exception) {
                // Fallback to standard browser intent if Custom Tabs fails
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                val activityContext = findActivity(context)
                try {
                    isTabLaunched = true
                    if (activityContext != null) {
                        activityContext.startActivity(intent)
                    } else {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                } catch (err: Exception) {
                    Toast.makeText(context, "Unable to open link", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
