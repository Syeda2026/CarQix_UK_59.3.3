package com.aaapp.appguru.myusedcarsaleuk.features.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

data class ToolItemData(
    val id: String,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val category: String = "Popular"
)

@Composable
fun SmartToolsScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val toolsList = listOf(
        ToolItemData("buying_advice", "Car Buying Advice & Tips", "Complete UK used car buyer guide, inspection checklist & legal rights.", Icons.Default.Lightbulb),
        ToolItemData("road_tax", "Road Tax (VED) Calculator", "Calculate official UK vehicle road tax bands and luxury car surcharges.", Icons.Default.ConfirmationNumber),
        ToolItemData("fuel_cost", "Fuel Cost Calculator", "Calculate total fuel costs and litres required for any trip distance.", Icons.Default.LocalGasStation),
        ToolItemData("loan", "Car Loan Calculator", "Calculate monthly car finance payments, interest charges and APR impact.", Icons.Default.Calculate),
        ToolItemData("mileage", "Annual Mileage Estimator", "Estimate commute & leisure mileage and projected servicing costs.", Icons.Default.Speed),
        ToolItemData("running_cost", "Running Cost Calculator", "Calculate total annual and monthly ownership costs for your car.", Icons.Default.AccountBalanceWallet),
        ToolItemData("journey_cost", "Journey & Travel Cost", "Calculate total trip cost including fuel, tolls, and congestion charges.", Icons.Default.Map),
        ToolItemData("fuel_economy", "Fuel Economy Converter", "Convert between UK MPG, Litres/100km and Km/Litre instantly.", Icons.Default.CompareArrows),
        ToolItemData("monthly_budget", "Monthly Car Budget", "Find your recommended maximum monthly car spend based on income.", Icons.Default.PieChart),
        ToolItemData("depreciation", "Vehicle Depreciation", "Estimate car value loss over 1, 3 and 5 years with mileage factor.", Icons.Default.TrendingDown),
        ToolItemData("insurance_estimate", "Insurance Group Estimator", "Estimate annual insurance premium tiers and cost reduction tips.", Icons.Default.Shield)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Smart Tools",
                subtitle = "10+ UK Automotive Calculators",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "smart_tools",
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
                    title = "Smart Automotive Calculators",
                    subtitle = "Instant, accurate tools designed for UK drivers and car buyers.",
                    ctaText = "Try Road Tax Calculator",
                    badgeText = "100% Free Tools",
                    onCtaClick = { onNavigateRoute("calculator_detail/road_tax") }
                )
            }

            item {
                Text(
                    text = "ALL AUTOMOTIVE CALCULATORS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            itemsIndexed(toolsList) { index, tool ->
                BlinkCard(
                    onClick = {
                        if (tool.id == "buying_advice") {
                            onNavigateRoute("buying_advice")
                        } else {
                            onNavigateRoute("calculator_detail/${tool.id}")
                        }
                    },
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (tool.id == "buying_advice") AccentGold.copy(alpha = 0.2f) else RoyalBlue.copy(alpha = 0.1f),
                            shape = CircleShape,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = tool.icon,
                                    contentDescription = tool.name,
                                    tint = if (tool.id == "buying_advice") AccentGoldDark else RoyalBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tool.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tool.description,
                                fontSize = 11.sp,
                                color = TextSecondaryLight,
                                lineHeight = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = RoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (index == 1) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isSmartToolsNativeAdEnabled
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
