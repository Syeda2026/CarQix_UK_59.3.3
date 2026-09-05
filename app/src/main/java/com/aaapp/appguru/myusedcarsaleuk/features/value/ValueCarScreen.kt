package com.aaapp.appguru.myusedcarsaleuk.features.value

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun ValueCarScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
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
                title = "Value My Car",
                subtitle = "Free Market Valuation",
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
                    title = "Value My Car",
                    subtitle = "Find out what your car is really worth in today's market – for free.",
                    ctaText = "Get Free Valuation",
                    badgeText = "Instant Online Quotes",
                    isRedirecting = isRedirecting,
                    onCtaClick = {
                        val url = ConfigManager.getResolvedUrl("autotrader_valuation")
                        ChromeTabManager.launchUrl(context, url) { isRedirecting = it }
                    }
                )
            }


            // Why check valuation?
            item {
                Text(
                    text = "WHY CHECK YOUR CAR'S VALUE?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ValueReasonItem("Market Value", "Know real worth", Icons.Default.TrendingUp, Modifier.weight(1f))
                    ValueReasonItem("Sell Confident", "Get fair offers", Icons.Default.Verified, Modifier.weight(1f))
                    ValueReasonItem("Avoid Lowball", "Negotiate well", Icons.Default.Shield, Modifier.weight(1f))
                    ValueReasonItem("100% Free", "Instant valuation", Icons.Default.Thunderstorm, Modifier.weight(1f))
                }
            }

            // Top Recommendation Card (AutoTrader Valuation)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Surface(
                            color = RoyalBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "⭐ Our Top Recommendation",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Get your free valuation from AutoTrader",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        listOf(
                            "UK's most trusted car marketplace data",
                            "Instant online valuation based on real market sales",
                            "Used by millions of UK car owners"
                        ).forEach { feature ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = feature, fontSize = 12.sp, color = TextPrimaryLight)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val url = ConfigManager.getResolvedUrl("autotrader_valuation")
                                ChromeTabManager.launchUrl(
                                    context = context,
                                    url = url,
                                    title = "AutoTrader Free Car Valuation UK",
                                    imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                ) { isRedirecting = it }
                            },
                            enabled = !isRedirecting,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RoyalBlue,
                                contentColor = Color.White,
                                disabledContainerColor = RoyalBlue,
                                disabledContentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (isRedirecting) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.5.dp,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Opening AutoTrader...", fontWeight = FontWeight.Bold)
                            } else {
                                Text(text = "Continue to AutoTrader", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                            }
                        }

                    }
                }
            }

            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isValuationNativeAdEnabled
                )
            }

            // How it works
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "How it works", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StepBadge("1", "Click Continue", "Opens AutoTrader", Modifier.weight(1f))
                            StepBadge("2", "Enter Details", "Provide registration", Modifier.weight(1f))
                            StepBadge("3", "Get Value", "Instant value quote", Modifier.weight(1f))
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ValueReasonItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = NavyDark,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                fontSize = 8.5.sp,
                color = TextSecondaryLight,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 10.sp,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun StepBadge(num: String, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Surface(color = RoyalBlue, shape = CircleShape, modifier = Modifier.size(22.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = num, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold,
            color = NavyDark,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
        Text(
            text = subtitle,
            fontSize = 8.5.sp,
            color = TextSecondaryLight,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 10.sp,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}
