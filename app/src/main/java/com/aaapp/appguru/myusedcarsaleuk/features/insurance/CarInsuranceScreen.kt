package com.aaapp.appguru.myusedcarsaleuk.features.insurance

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
fun CarInsuranceScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val providers = AutomotiveDataProvider.insuranceProviders

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Car Insurance",
                subtitle = "Compare Top UK Insurers",
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
                    title = "Car Insurance",
                    subtitle = "Compare quotes from over 100+ UK insurers and save hundreds on your annual premium.",
                    ctaText = "Estimate Insurance",
                    badgeText = "Save Up To £450*",
                    onCtaClick = { onNavigateRoute("calculator_detail/insurance_estimate") }
                )
            }

            // Estimate Calculator Shortcut Card
            item {
                BlinkCard(
                    onClick = { onNavigateRoute("calculator_detail/insurance_estimate") },
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
                        Icon(imageVector = Icons.Default.Calculate, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Try Insurance Group Calculator", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                            Text(text = "Estimate annual insurance premiums based on age, NCB & vehicle group", fontSize = 11.sp, color = TextSecondaryLight)
                        }
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextSecondaryLight, modifier = Modifier.size(16.dp))
                    }
                }
            }

            item {
                Text(
                    text = "COMPARE LEADING COMPARISON SITES",
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
                    ctaText = "Compare on ${provider.name}",
                    badge = provider.badge ?: provider.keyRateOrFeature,
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl(provider.partnerKey)
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "${provider.name} - UK Car Insurance",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )

                if (index == 0) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isInsuranceNativeAdEnabled
                    )
                }
            }


            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
