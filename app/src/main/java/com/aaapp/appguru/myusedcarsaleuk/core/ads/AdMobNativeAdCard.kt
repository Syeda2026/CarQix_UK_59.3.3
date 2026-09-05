package com.aaapp.appguru.myusedcarsaleuk.core.ads

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * Reusable Compose component for AdMob Native Ads with Material 3 styling
 * and independent screen-level feature toggles.
 */
@Composable
fun AdMobNativeAdCard(
    modifier: Modifier = Modifier,
    isPlacementEnabled: Boolean = true,
    adUnitId: String? = null,
    showMedia: Boolean = false
) {
    val adsConfig = ConfigManager.adsConfig
    val activeUnitId = (adUnitId ?: adsConfig.nativeAdUnitId)

    if (!adsConfig.isAdsEnabled || !adsConfig.isNativeAdsEnabled || !isPlacementEnabled) {
        return
    }

    val isInspection = LocalInspectionMode.current
    if (isInspection) {
        // Preview placeholder in Compose preview/inspection
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(12.dp),
                color = androidx.compose.ui.graphics.Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFFE2E8F0))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Sponsored Native Ad Preview",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color(0xFF0F172A)
                    )
                }
            }
        }
        return
    }

    val context = LocalContext.current
    var nativeAdState by remember { mutableStateOf<NativeAd?>(null) }
    var isLoaded by remember { mutableStateOf(false) }
    var shouldStartLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Delay ad loading slightly to let the UI settle on launch
        kotlinx.coroutines.delay(600)
        shouldStartLoading = true
    }

    DisposableEffect(activeUnitId, shouldStartLoading) {
        if (!shouldStartLoading) return@DisposableEffect onDispose {}

        Log.d("AdMobNativeAdCard", "Loading Native Ad for unit: $activeUnitId")
        val adLoader = AdLoader.Builder(context, activeUnitId)
            .forNativeAd { ad ->
                Log.d("AdMobNativeAdCard", "Native Ad loaded successfully for unit: $activeUnitId")
                nativeAdState?.destroy()
                nativeAdState = ad
                isLoaded = true
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w("AdMobNativeAdCard", "Native ad failed to load (unit: $activeUnitId): code=${error.code}, message=${error.message}")
                    isLoaded = false
                    nativeAdState = null
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                    .build()
            )
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

        onDispose {
            nativeAdState?.destroy()
            nativeAdState = null
        }
    }

    if (isLoaded && nativeAdState != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 6.dp)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    createNativeAdCardView(ctx, showMedia)
                },
                update = { view ->
                    nativeAdState?.let { ad ->
                        populateNativeAdView(view, ad, showMedia)
                    }
                }
            )
        }
    }
}

private fun dpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

private fun createNativeAdCardView(context: Context, showMedia: Boolean): NativeAdView {
    val nativeAdView = NativeAdView(context)
    nativeAdView.layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    // Outer Card Container (LinearLayout with rounded background and border)
    val cardLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val p = dpToPx(context, 14f)
        setPadding(p, p, p, p)

        val cardBg = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = dpToPx(context, 12f).toFloat()
            setStroke(dpToPx(context, 1f), Color.parseColor("#E2E8F0"))
        }
        background = cardBg
        elevation = dpToPx(context, 2f).toFloat()
    }

    // Top Row: "Ad" badge + Advertiser / Attribution
    val topRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    val adBadge = TextView(context).apply {
        text = "Ad"
        setTextColor(Color.parseColor("#0F172A"))
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        setTypeface(null, Typeface.BOLD)
        val badgePaddingH = dpToPx(context, 6f)
        val badgePaddingV = dpToPx(context, 2f)
        setPadding(badgePaddingH, badgePaddingV, badgePaddingH, badgePaddingV)

        val bg = GradientDrawable().apply {
            setColor(Color.parseColor("#FEF08A")) // Soft Gold accent
            cornerRadius = dpToPx(context, 4f).toFloat()
            setStroke(dpToPx(context, 1f), Color.parseColor("#FACC15"))
        }
        background = bg
    }
    topRow.addView(adBadge)

    val advertiserText = TextView(context).apply {
        tag = "advertiser"
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
        setTextColor(Color.parseColor("#64748B"))
        val ml = dpToPx(context, 8f)
        setPadding(ml, 0, 0, 0)
    }
    topRow.addView(advertiserText)
    cardLayout.addView(topRow)

    // Middle Row: Icon + Headline & Body
    val contentRow = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = dpToPx(context, 10f)
        layoutParams = lp
    }

    val iconView = ImageView(context).apply {
        tag = "icon"
        val size = dpToPx(context, 44f)
        layoutParams = LinearLayout.LayoutParams(size, size).apply {
            marginEnd = dpToPx(context, 12f)
        }
        scaleType = ImageView.ScaleType.FIT_CENTER
    }
    contentRow.addView(iconView)

    val textCol = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    }

    val headlineView = TextView(context).apply {
        tag = "headline"
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        setTypeface(null, Typeface.BOLD)
        setTextColor(Color.parseColor("#0F172A"))
        maxLines = 1
        ellipsize = android.text.TextUtils.TruncateAt.END
    }
    textCol.addView(headlineView)

    val bodyView = TextView(context).apply {
        tag = "body"
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        setTextColor(Color.parseColor("#475569"))
        maxLines = 2
        ellipsize = android.text.TextUtils.TruncateAt.END
        setPadding(0, dpToPx(context, 2f), 0, 0)
    }
    textCol.addView(bodyView)
    contentRow.addView(textCol)
    cardLayout.addView(contentRow)

    // Optional MediaView
    if (showMedia) {
        val mediaView = MediaView(context).apply {
            tag = "media"
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(context, 140f)
            )
            lp.topMargin = dpToPx(context, 10f)
            layoutParams = lp
        }
        cardLayout.addView(mediaView)
    }

    // Bottom Row: Call To Action Button
    val ctaButton = Button(context).apply {
        tag = "cta"
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(context, 38f)
        )
        lp.topMargin = dpToPx(context, 12f)
        layoutParams = lp
        setBackgroundColor(Color.parseColor("#1D4ED8")) // Royal Blue
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
        setTypeface(null, Typeface.BOLD)
        stateListAnimator = null

        val btnBg = GradientDrawable().apply {
            setColor(Color.parseColor("#1D4ED8"))
            cornerRadius = dpToPx(context, 8f).toFloat()
        }
        background = btnBg
    }
    cardLayout.addView(ctaButton)

    nativeAdView.addView(cardLayout)

    // Register Native Ad View component references
    nativeAdView.headlineView = headlineView
    nativeAdView.bodyView = bodyView
    nativeAdView.iconView = iconView
    nativeAdView.callToActionView = ctaButton
    nativeAdView.advertiserView = advertiserText
    if (showMedia) {
        nativeAdView.mediaView = cardLayout.findViewWithTag("media")
    }

    return nativeAdView
}

private fun populateNativeAdView(nativeAdView: NativeAdView, nativeAd: NativeAd, showMedia: Boolean) {
    // Headline
    (nativeAdView.headlineView as? TextView)?.text = nativeAd.headline

    // Body
    (nativeAdView.bodyView as? TextView)?.apply {
        if (nativeAd.body != null) {
            text = nativeAd.body
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }

    // Call to Action
    (nativeAdView.callToActionView as? Button)?.apply {
        if (nativeAd.callToAction != null) {
            text = nativeAd.callToAction
            visibility = View.VISIBLE
        } else {
            text = "Learn More"
            visibility = View.VISIBLE
        }
    }

    // Icon
    (nativeAdView.iconView as? ImageView)?.apply {
        if (nativeAd.icon?.drawable != null) {
            setImageDrawable(nativeAd.icon?.drawable)
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }

    // Advertiser
    (nativeAdView.advertiserView as? TextView)?.apply {
        if (nativeAd.advertiser != null) {
            text = "• ${nativeAd.advertiser}"
            visibility = View.VISIBLE
        } else if (nativeAd.store != null) {
            text = "• ${nativeAd.store}"
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }

    // Media
    if (showMedia) {
        nativeAdView.mediaView?.let { mediaView ->
            if (nativeAd.mediaContent != null) {
                mediaView.mediaContent = nativeAd.mediaContent
                mediaView.visibility = View.VISIBLE
            } else {
                mediaView.visibility = View.GONE
            }
        }
    }

    // Bind full native ad to container
    nativeAdView.setNativeAd(nativeAd)
}
