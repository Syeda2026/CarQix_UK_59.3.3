package com.aaapp.appguru.myusedcarsaleuk.features.sell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun SellCarScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val providers = AutomotiveDataProvider.sellingProviders
    var selectedProviderId by remember { mutableStateOf(providers.first().id) }
    val activeProvider = providers.firstOrNull { it.id == selectedProviderId } ?: providers.first()
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
                title = "Sell Your Car",
                subtitle = "Compare UK Buyers & Dealers",
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
                    title = "Sell Your Car",
                    subtitle = "Compare trusted UK marketplaces and choose the best way to sell your vehicle.",
                    ctaText = "Get Started",
                    badgeText = "100% Free Service",
                    onCtaClick = { }
                )
            }

            // Selling Trust Indicators
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SellingBenefitBadge("Hand-picked UK marketplaces", Icons.Default.Verified, Modifier.weight(1f))
                        SellingBenefitBadge("See what each offers", Icons.Default.Compare, Modifier.weight(1f))
                        SellingBenefitBadge("Pick the best option", Icons.Default.ThumbUp, Modifier.weight(1f))
                    }
                }
            }

            item {
                Text(
                    text = "CHOOSE A MARKETPLACE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            items(providers) { provider ->
                val isSelected = provider.id == selectedProviderId
                Card(
                    onClick = { selectedProviderId = provider.id },
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, RoyalBlue) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedProviderId = provider.id },
                            colors = RadioButtonDefaults.colors(selectedColor = RoyalBlue)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = provider.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = NavyDark,
                                    modifier = Modifier.weight(1f, fill = false),
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = AccentGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(text = provider.rating.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NavyDark)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            provider.benefits.forEach { benefit ->
                                Text(
                                    text = "• $benefit",
                                    fontSize = 11.sp,
                                    color = TextSecondaryLight,
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val resolvedUrl = ConfigManager.getResolvedUrl(activeProvider.partnerKey)
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = resolvedUrl,
                            title = "Sell Car on ${activeProvider.name} UK",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        ) { loading ->
                            isRedirecting = loading
                        }
                    },
                    enabled = !isRedirecting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RoyalBlue,
                        contentColor = Color.White,
                        disabledContainerColor = RoyalBlue,
                        disabledContentColor = Color.White
                    )
                ) {
                    if (isRedirecting) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.5.dp,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Opening ${activeProvider.name}...",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Continue with ${activeProvider.name}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
                Text(
                    text = "🔒 You will continue on ${activeProvider.name}'s website to complete your listing.",
                    fontSize = 10.5.sp,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                )
            }

            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isSellCarNativeAdEnabled
                )
            }

            // Need Help Selling Cards
            item {
                Text(
                    text = "NEED HELP SELLING YOUR CAR?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HelpOptionCard("Value My Car", "Check car's value", Icons.Default.TrendingUp, Modifier.weight(1f)) {
                            onNavigateRoute("value_car")
                        }
                        HelpOptionCard("Vehicle History", "Show trust records", Icons.Default.FactCheck, Modifier.weight(1f)) {
                            onNavigateRoute("vehicle_history")
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun SellingBenefitBadge(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = NavyDark,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            lineHeight = 12.sp,
            maxLines = 2,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HelpOptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BlinkCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    color = NavyDark,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 9.5.sp,
                    color = TextSecondaryLight,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    lineHeight = 12.sp
                )
            }
        }
    }
}
