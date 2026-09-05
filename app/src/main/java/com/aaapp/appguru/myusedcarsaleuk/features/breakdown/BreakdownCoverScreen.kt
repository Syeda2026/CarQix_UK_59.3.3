package com.aaapp.appguru.myusedcarsaleuk.features.breakdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun BreakdownCoverScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val providers = AutomotiveDataProvider.breakdownProviders
    var isRedirecting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Breakdown Cover",
                subtitle = "24/7 UK Roadside Assistance",
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
                    title = "Breakdown Cover",
                    subtitle = "Compare 24/7 UK breakdown policies from AA, RAC, Green Flag & Britannia Rescue.",
                    ctaText = "Compare Breakdown Cover",
                    badgeText = "Fix 4 out of 5 at Roadside",
                    isRedirecting = isRedirecting,
                    onCtaClick = {
                        val url = ConfigManager.getResolvedUrl("theaa_breakdown")
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "AA Breakdown Cover UK",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        ) { isRedirecting = it }
                    }
                )
            }

            item {
                Text(
                    text = "LEADING UK BREAKDOWN PROVIDERS",
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
                    ctaText = "View Cover on ${provider.name}",
                    badge = provider.badge ?: provider.keyRateOrFeature,
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl(provider.partnerKey)
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "${provider.name} - UK Breakdown Cover",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )

                if (index == 0) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isBreakdownNativeAdEnabled
                    )
                }
            }


            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
