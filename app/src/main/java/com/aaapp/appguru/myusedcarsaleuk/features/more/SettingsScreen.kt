package com.aaapp.appguru.myusedcarsaleuk.features.more

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    dataStoreManager: DataStoreManager,
    onNavigateRoute: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                subtitle = "App Preferences & Options",
                showBackButton = true,
                onBackClick = { onNavigateRoute("more") },
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

            // Localization & Standards Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Flag, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Region & Display Standards", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "• Currency: GBP (£)", fontSize = 12.sp, color = TextPrimaryLight)
                        Text(text = "• Units: Miles & MPG (UK Standard)", fontSize = 12.sp, color = TextPrimaryLight)
                        Text(text = "• Tax Rates: DVLA VED Bands 2024/2025", fontSize = 12.sp, color = TextPrimaryLight)
                    }
                }
            }

            // Community & Play Store Actions Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "App Store & Community", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Help us grow or check for the latest features on Google Play.", fontSize = 11.5.sp, color = TextSecondaryLight)
                        Spacer(modifier = Modifier.height(12.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { shareApp(context) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Share App with Friends")
                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { openPlayStore(context) },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Rate App", fontSize = 12.5.sp)
                                }

                                Button(
                                    onClick = { openPlayStore(context) },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = AccentGold, contentColor = NavyDark),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.SystemUpdate, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Update App", fontSize = 12.5.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Onboarding Control
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "App Guide & Onboarding", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Re-open the welcome guide and feature overview.", fontSize = 11.5.sp, color = TextSecondaryLight)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    dataStoreManager.setOnboardingCompleted(false)
                                    onNavigateRoute("onboarding")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("View App Guide")
                        }
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

