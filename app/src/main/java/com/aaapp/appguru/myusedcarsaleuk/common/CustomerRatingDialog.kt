package com.aaapp.appguru.myusedcarsaleuk.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun CustomerRatingDialog(
    onDismiss: () -> Unit,
    onRateNow: () -> Unit
) {
    val context = LocalContext.current
    var selectedStars by remember { mutableIntStateOf(5) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .testTag("customer_rating_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header badge
                Surface(
                    color = AccentGold.copy(alpha = 0.2f),
                    shape = CircleShape,
                    modifier = Modifier.size(68.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Surface(
                            color = AccentGold,
                            shape = CircleShape,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.RateReview,
                                    contentDescription = "Rate Experience",
                                    tint = NavyDark,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Enjoying CarQix UK?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Your feedback helps us keep the app 100% free with live UK car deals, MOT records, and smart finance tools.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Interactive 5 Gold Stars
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    for (starIndex in 1..5) {
                        val isFilled = starIndex <= selectedStars
                        IconButton(
                            onClick = {
                                selectedStars = starIndex
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("rating_star_$starIndex")
                        ) {
                            Icon(
                                imageVector = if (isFilled) Icons.Default.Star else Icons.Outlined.StarOutline,
                                contentDescription = "$starIndex Stars",
                                tint = if (isFilled) AccentGold else Color(0xFFCBD5E1),
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }
                }

                Text(
                    text = when (selectedStars) {
                        5 -> "Excellent! Highly recommended ❤️"
                        4 -> "Great experience 👍"
                        3 -> "Good, keeping improved 🙂"
                        2 -> "Needs improvement"
                        else -> "Give us your feedback"
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedStars >= 4) AccentGoldDark else TextSecondaryLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Rate Now Primary Button
                Button(
                    onClick = {
                        launchPlayStore(context)
                        onRateNow()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("rate_now_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rate on Google Play",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Maybe Later Secondary Button
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("rate_later_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Maybe Later",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondaryLight
                    )
                }
            }
        }
    }
}

private fun launchPlayStore(context: Context) {
    val packageName = context.packageName
    val playStoreUri = Uri.parse("market://details?id=$packageName")
    val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
    
    val marketIntent = Intent(Intent.ACTION_VIEW, playStoreUri).apply {
        addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
            Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    try {
        context.startActivity(marketIntent)
    } catch (e: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(webIntent)
        } catch (err: Exception) {
            // Ignored if no browser/store available
        }
    }
}
