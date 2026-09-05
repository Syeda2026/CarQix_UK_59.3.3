package com.aaapp.appguru.myusedcarsaleuk.features.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun VehicleHistoryScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val providers = AutomotiveDataProvider.historyProviders
    var registrationInput by remember { mutableStateOf("") }
    var isRedirecting by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (ChromeTabManager.openingTabState.value == null) {
                    isRedirecting = false
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        ChromeTabManager.openingTabState.collect { state ->
            if (state == null) {
                isRedirecting = false
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Vehicle History",
                subtitle = "Official DVLA & HPI Data",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "home",
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            item {
                HeroBanner(
                    title = "Vehicle History Check",
                    subtitle = "Uncover hidden history, outstanding finance, mileage anomalies, write-off records & stolen alerts.",
                    ctaText = "Run History Check",
                    badgeText = "Official DVLA & Police Data",
                    isRedirecting = isRedirecting,
                    onCtaClick = {
                        val url = ConfigManager.getResolvedUrl("gov_mot")
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "GOV.UK Vehicle MOT & History Check",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        ) { isRedirecting = it }
                    }
                )
            }

            // Quick VRN Lookup Bar
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "ENTER REGISTRATION NUMBER (VRN)", fontWeight = FontWeight.Black, fontSize = 11.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            AppTextField(
                                value = registrationInput,
                                onValueChange = { registrationInput = it.uppercase() },
                                placeholder = "e.g. AB12 CDE",
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                leadingIcon = {
                                    Surface(color = RoyalBlue, shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(6.dp)) {
                                        Text("GB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(4.dp))
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val url = ConfigManager.getResolvedUrl("gov_mot")
                                    val title = if (registrationInput.isNotBlank()) "MOT Check for $registrationInput" else "GOV.UK MOT History"
                                    ChromeTabManager.launchUrl(
                                        context = context,
                                        url = url,
                                        title = title,
                                        imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                    ) { isRedirecting = it }
                                },
                                enabled = !isRedirecting,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AccentGold,
                                    contentColor = NavyDark,
                                    disabledContainerColor = AccentGold,
                                    disabledContentColor = NavyDark
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(54.dp)
                            ) {
                                if (isRedirecting) {
                                    CircularProgressIndicator(
                                        color = NavyDark,
                                        strokeWidth = 2.dp,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Text("Check", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }


            // What a History Check Covers
            item {
                Text(
                    text = "WHAT OUR PARTNER CHECKS INCLUDE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            CheckFeatureItem("Stolen Alert", Icons.Default.Security, Modifier.weight(1f))
                            CheckFeatureItem("Write-off Cat", Icons.Default.ReportProblem, Modifier.weight(1f))
                            CheckFeatureItem("Finance Owed", Icons.Default.Receipt, Modifier.weight(1f))
                            CheckFeatureItem("Odometer Check", Icons.Default.Speed, Modifier.weight(1f))
                        }
                    }
                }
            }

            // History Providers List
            item {
                Text(
                    text = "OFFICIAL HISTORY CHECK PROVIDERS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            itemsIndexed(providers) { index, provider ->
                PartnerCard(
                    name = provider.name,
                    description = provider.description,
                    rating = provider.rating,
                    reviewsCount = provider.reviewsCount,
                    benefits = provider.benefits,
                    ctaText = "Check on ${provider.name}",
                    badge = provider.badge ?: provider.keyRateOrFeature,
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl(provider.partnerKey)
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "${provider.name} - Vehicle History Check",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )

                if (index == 0) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isVehicleHistoryNativeAdEnabled
                    )
                }
            }


            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun CheckFeatureItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Surface(color = RoyalBlue.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp), modifier = Modifier.size(34.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(17.dp))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = NavyDark,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            maxLines = 2,
            lineHeight = 11.sp,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}
