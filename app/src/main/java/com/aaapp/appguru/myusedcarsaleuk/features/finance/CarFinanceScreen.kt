package com.aaapp.appguru.myusedcarsaleuk.features.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun CarFinanceScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val providers = AutomotiveDataProvider.financeProviders

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Car Finance",
                subtitle = "Compare Trusted Lenders",
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
                    title = "Car Finance",
                    subtitle = "Compare trusted lenders and find the right finance deal for you.",
                    ctaText = "Calculate Monthly Cost",
                    badgeText = "Soft Credit Check Only",
                    onCtaClick = { onNavigateRoute("calculator_detail/loan") }
                )
            }

            // Finance Calculators & Tools Section Header & Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "FINANCE CALCULATORS & TOOLS",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark,
                            letterSpacing = 0.5.sp
                        )
                        BlinkTextButton(
                            onClick = { onNavigateRoute("smart_tools") },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "View All >",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBlue
                            )
                        }
                    }

                    data class FinanceCalcItem(
                        val id: String,
                        val name: String,
                        val icon: ImageVector,
                        val color: Color
                    )

                    val financeTools = listOf(
                        FinanceCalcItem("loan", "Finance Calc", Icons.Default.Calculate, Color(0xFF8E24AA)),
                        FinanceCalcItem("monthly_budget", "Monthly Budget", Icons.Default.PieChart, RoyalBlue),
                        FinanceCalcItem("loan", "Hire Purchase", Icons.Default.Key, SuccessGreen),
                        FinanceCalcItem("loan", "PCP Calculator", Icons.Default.Repeat, AccentGoldDark),
                        FinanceCalcItem("running_cost", "Running Cost", Icons.Default.AccountBalanceWallet, RoyalBlue),
                        FinanceCalcItem("depreciation", "Depreciation", Icons.Default.TrendingDown, Color(0xFFE91E63)),
                        FinanceCalcItem("loan", "Personal Loan", Icons.Default.AccountBalance, Color(0xFF00897B))
                    )

                    BoxWithConstraints {
                        if (maxWidth >= 600.dp) {
                            val calcRows = financeTools.chunked(4)
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                calcRows.forEach { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItems.forEach { tool ->
                                            Box(modifier = Modifier.weight(1f)) {
                                                BlinkCard(
                                                    onClick = {
                                                        onNavigateRoute("calculator_detail/${tool.id}")
                                                    },
                                                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                                    shape = RoundedCornerShape(12.dp),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(84.dp)
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(6.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Surface(
                                                            color = tool.color.copy(alpha = 0.12f),
                                                            shape = CircleShape,
                                                            modifier = Modifier.size(32.dp)
                                                        ) {
                                                            Box(contentAlignment = Alignment.Center) {
                                                                Icon(
                                                                    imageVector = tool.icon,
                                                                    contentDescription = tool.name,
                                                                    tint = tool.color,
                                                                    modifier = Modifier.size(17.dp)
                                                                )
                                                            }
                                                        }
                                                        Spacer(modifier = Modifier.height(6.dp))
                                                        Text(
                                                            text = tool.name,
                                                            fontSize = 10.5.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = NavyDark,
                                                            textAlign = TextAlign.Center,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        if (rowItems.size < 4) {
                                            repeat(4 - rowItems.size) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(vertical = 2.dp)
                            ) {
                                items(financeTools) { tool ->
                                    BlinkCard(
                                        onClick = {
                                            onNavigateRoute("calculator_detail/${tool.id}")
                                        },
                                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .width(104.dp)
                                            .height(84.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Surface(
                                                color = tool.color.copy(alpha = 0.12f),
                                                shape = CircleShape,
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Icon(
                                                        imageVector = tool.icon,
                                                        contentDescription = tool.name,
                                                        tint = tool.color,
                                                        modifier = Modifier.size(17.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = tool.name,
                                                fontSize = 10.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = NavyDark,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Loan Calculator Shortcut Banner
            item {
                BlinkCard(
                    onClick = { onNavigateRoute("calculator_detail/loan") },
                    colors = CardDefaults.cardColors(containerColor = RoyalBlue.copy(alpha = 0.08f)),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoyalBlue.copy(alpha = 0.3f))
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
                            Text(text = "Use Our Car Loan Calculator", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                            Text(text = "Calculate exact monthly payments and total interest instantly", fontSize = 11.sp, color = TextSecondaryLight)
                        }
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Finance Lenders List
            item {
                Text(
                    text = "COMPARE TRUSTED FINANCE PARTNERS",
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
                    ctaText = "Continue to ${provider.name}",
                    badge = provider.badge ?: provider.keyRateOrFeature,
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl(provider.partnerKey)
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "${provider.name} - UK Car Finance",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )

                if (index == 0) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isFinanceNativeAdEnabled
                    )
                }
            }


            // FCA Regulatory Compliance Box
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Safe. Secure. Transparent.", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark)
                            Text(
                                text = "We only connect users with FCA authorised lenders. We do not perform credit checks or collect financial data directly.",
                                fontSize = 10.sp,
                                color = TextSecondaryLight,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
