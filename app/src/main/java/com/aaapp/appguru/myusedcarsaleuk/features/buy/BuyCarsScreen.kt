package com.aaapp.appguru.myusedcarsaleuk.features.buy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun BuyCarsScreen(
    viewModel: BuyCarsViewModel,
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val marketplaces = AutomotiveDataProvider.marketplaces
    val savedItems by viewModel.savedItems.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Buy Used Cars",
                subtitle = "UK Marketplace Guide & Calculators",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onSearchClick = { onNavigateRoute("global_search") },
                onSavedClick = { onNavigateRoute("saved") },
                savedCount = savedItems.size
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "buy",
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

            // 1. Hero Banner
            item {
                HeroBanner(
                    title = "Buy Used Cars on CarQix UK",
                    subtitle = "Compare top verified UK marketplaces and calculate your buying budget before making an offer.",
                    ctaText = "Car Loan Calculator",
                    badgeText = "100% Free Buyer Guide",
                    onCtaClick = { onNavigateRoute("calculator_detail/loan") }
                )
            }

            // 2. Calculators & Advice Section (Header + Cards)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "HELPFUL CAR BUYING CALCULATORS & ADVICE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = NavyDark
                    )
                    BoxWithConstraints {
                        if (maxWidth >= 600.dp) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    HorizontalToolCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Buying Advice",
                                        description = "10 inspection steps",
                                        icon = Icons.Default.Lightbulb,
                                        iconColor = AccentGoldDark,
                                        onClick = { onNavigateRoute("buying_advice") }
                                    )
                                    HorizontalToolCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Loan Calculator",
                                        description = "Monthly payments",
                                        icon = Icons.Default.Calculate,
                                        iconColor = RoyalBlue,
                                        onClick = { onNavigateRoute("calculator_detail/loan") }
                                    )
                                    HorizontalToolCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Fuel & EV Costs",
                                        description = "Compare fuel costs",
                                        icon = Icons.Default.LocalGasStation,
                                        iconColor = SuccessGreen,
                                        onClick = { onNavigateRoute("calculator_detail/fuel") }
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    HorizontalToolCard(
                                        modifier = Modifier.weight(1f),
                                        title = "History Check",
                                        description = "Free MOT history",
                                        icon = Icons.Default.History,
                                        iconColor = RoyalBlueLight,
                                        onClick = { onNavigateRoute("vehicle_history") }
                                    )
                                    HorizontalToolCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Valuation Tool",
                                        description = "Car market value",
                                        icon = Icons.Default.PriceCheck,
                                        iconColor = AccentGoldDark,
                                        onClick = { onNavigateRoute("value_car") }
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        } else {
                            val calcScrollState = rememberScrollState()
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(calcScrollState),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                HorizontalToolCard(
                                    modifier = Modifier.width(140.dp),
                                    title = "Buying Advice",
                                    description = "10 inspection steps",
                                    icon = Icons.Default.Lightbulb,
                                    iconColor = AccentGoldDark,
                                    onClick = { onNavigateRoute("buying_advice") }
                                )
                                HorizontalToolCard(
                                    modifier = Modifier.width(140.dp),
                                    title = "Loan Calculator",
                                    description = "Monthly payments",
                                    icon = Icons.Default.Calculate,
                                    iconColor = RoyalBlue,
                                    onClick = { onNavigateRoute("calculator_detail/loan") }
                                )
                                HorizontalToolCard(
                                    modifier = Modifier.width(140.dp),
                                    title = "Fuel & EV Costs",
                                    description = "Compare fuel costs",
                                    icon = Icons.Default.LocalGasStation,
                                    iconColor = SuccessGreen,
                                    onClick = { onNavigateRoute("calculator_detail/fuel") }
                                )
                                HorizontalToolCard(
                                    modifier = Modifier.width(140.dp),
                                    title = "History Check",
                                    description = "Free MOT history",
                                    icon = Icons.Default.History,
                                    iconColor = RoyalBlueLight,
                                    onClick = { onNavigateRoute("vehicle_history") }
                                )
                                HorizontalToolCard(
                                    modifier = Modifier.width(140.dp),
                                    title = "Valuation Tool",
                                    description = "Car market value",
                                    icon = Icons.Default.PriceCheck,
                                    iconColor = AccentGoldDark,
                                    onClick = { onNavigateRoute("value_car") }
                                )
                            }
                        }
                    }
                }
            }

            // 3. Recommended Marketplace Cards
            itemsIndexed(marketplaces) { index, marketplace ->
                if (index == 0) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "COMPARE TOP UK CAR MARKETPLACES",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark
                        )
                        PartnerCard(
                            name = marketplace.name,
                            description = marketplace.description,
                            rating = marketplace.rating,
                            reviewsCount = marketplace.reviewsCount,
                            benefits = marketplace.benefits,
                            ctaText = "Continue to ${marketplace.name}",
                            badge = marketplace.badge ?: marketplace.tagLine,
                            onContinueClick = {
                                val url = ConfigManager.getResolvedUrl(marketplace.webUrlKey)
                                ChromeTabManager.launchUrl(
                                    context = context,
                                    url = url,
                                    title = "${marketplace.name} - Used Cars UK",
                                    imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                )
                            }
                        )
                    }
                } else {
                    PartnerCard(
                        name = marketplace.name,
                        description = marketplace.description,
                        rating = marketplace.rating,
                        reviewsCount = marketplace.reviewsCount,
                        benefits = marketplace.benefits,
                        ctaText = "Continue to ${marketplace.name}",
                        badge = marketplace.badge ?: marketplace.tagLine,
                        onContinueClick = {
                            val url = ConfigManager.getResolvedUrl(marketplace.webUrlKey)
                            ChromeTabManager.launchUrl(
                                context = context,
                                url = url,
                                title = "${marketplace.name} - Used Cars UK",
                                imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                            )
                        }
                    )
                }

                if (index == 1) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isBuyCarsNativeAdEnabled
                    )
                }
            }

            // 7. How It Works Steps (3 Steps using "Value My Car" style at the end)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "How it works",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StepBadge("1", "Check Advice", "Inspection guide", Modifier.weight(1f))
                            StepBadge("2", "Calculate Budget", "Finance & running costs", Modifier.weight(1f))
                            StepBadge("3", "Compare & Buy", "Trusted marketplaces", Modifier.weight(1f))
                        }
                    }
                }
            }

            // 8. Safe Buyer Shield Trust Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Safe & Independent Buyer Guide",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "We only connect you directly with legitimate, established UK car marketplaces. Always view cars in person and verify V5C logbooks before payment.",
                                fontSize = 11.sp,
                                color = TextSecondaryLight,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun HorizontalToolCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    BlinkCard(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(17.dp)
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = TextSecondaryLight,
                    modifier = Modifier.size(14.dp)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 10.sp,
                    color = TextSecondaryLight,
                    maxLines = 1,
                    lineHeight = 13.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subtitle,
            fontSize = 8.5.sp,
            color = TextSecondaryLight,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 10.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}
