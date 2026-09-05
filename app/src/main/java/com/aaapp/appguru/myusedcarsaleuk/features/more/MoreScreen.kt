package com.aaapp.appguru.myusedcarsaleuk.features.more

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun MoreScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "More & Settings",
                subtitle = "App Options & Legal",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "more",
                onNavigate = onNavigateRoute
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateBackground),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 4.dp,
                bottom = padding.calculateBottomPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // App Identity Header Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = NavyDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = AccentGold, shape = CircleShape, modifier = Modifier.size(50.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.DirectionsCar, contentDescription = null, tint = NavyDark, modifier = Modifier.size(28.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(text = ConfigManager.appConfig.appName, fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color.White)
                            Text(text = "Your Trusted UK Car Buying Companion", fontSize = 11.5.sp, color = Color.White.copy(0.85f))
                            Text(text = "Version v${ConfigManager.appConfig.appVersion}", fontSize = 10.5.sp, color = AccentGold, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item { Text(text = "USER GUIDE & HELP", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MoreMenuRow("How to Use It", "Step-by-step walkthrough & heart button guide", Icons.Default.MenuBook) {
                        onNavigateRoute("how_to_use")
                    }
                }
            }

            item { Text(text = "SETTINGS & PREFERENCES", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MoreMenuRow("App Preferences & Options", "Display standards, guide & settings", Icons.Default.Settings) {
                        onNavigateRoute("settings")
                    }
                    MoreMenuRow("About CarQix UK", "App overview, key features & support", Icons.Default.Info) {
                        onNavigateRoute("about")
                    }
                }
            }

            item { Text(text = "SHARE & FEEDBACK", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MoreMenuRow("Share App", "Recommend CarQix UK to friends & family", Icons.Default.Share) {
                        shareApp(context)
                    }
                    MoreMenuRow("Rate This App", "Leave a rating or review on Google Play", Icons.Default.Star) {
                        openPlayStore(context)
                    }
                    MoreMenuRow("Update App", "Check Google Play Store for the latest updates", Icons.Default.SystemUpdate) {
                        openPlayStore(context)
                    }
                }
            }

            item { Text(text = "LEGAL & COMPLIANCE", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MoreMenuRow("Privacy Policy", "How we protect your data", Icons.Default.Security) {
                        onNavigateRoute("privacy_policy")
                    }
                    MoreMenuRow("Terms of Service", "Conditions of app usage", Icons.Default.Description) {
                        onNavigateRoute("terms")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

private fun shareApp(context: Context) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "Check out CarQix UK - Your complete UK used car companion app! Download now on Google Play: https://play.google.com/store/apps/details?id=com.aaapp.appguru.myusedcarsaleuk"
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share CarQix UK")
    context.startActivity(shareIntent)
}

private fun openPlayStore(context: Context) {
    val packageName = "com.aaapp.appguru.myusedcarsaleuk"
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

@Composable
private fun MoreMenuRow(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    BlinkCard(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(color = RoyalBlue.copy(0.1f), shape = CircleShape, modifier = Modifier.size(36.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                Text(text = subtitle, fontSize = 11.sp, color = TextSecondaryLight)
            }
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextSecondaryLight, modifier = Modifier.size(16.dp))
        }
    }
}
