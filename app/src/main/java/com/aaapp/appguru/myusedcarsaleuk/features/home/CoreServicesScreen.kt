package com.aaapp.appguru.myusedcarsaleuk.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

data class CoreServiceDetailItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val route: String,
    val badge: String
)

@Composable
fun CoreServicesScreen(
    onNavigateRoute: (String) -> Unit
) {
    val services = remember {
        listOf(
            CoreServiceDetailItem(
                title = "Buy Used Cars",
                description = "Browse thousands of quality checked used cars for sale across the UK with advanced filters.",
                icon = Icons.Default.DirectionsCar,
                color = RoyalBlue,
                route = "buy_cars",
                badge = "Search & Compare"
            ),
            CoreServiceDetailItem(
                title = "Sell Your Car",
                description = "Get an instant free valuation and sell your car fast to verified UK dealers or private buyers.",
                icon = Icons.Default.Sell,
                color = SuccessGreen,
                route = "sell_car",
                badge = "Free Valuation"
            ),
            CoreServiceDetailItem(
                title = "Value My Car",
                description = "Real-time UK market car valuation. Get accurate trade-in and private resale pricing estimates.",
                icon = Icons.Default.TrendingUp,
                color = AccentGoldDark,
                route = "value_car",
                badge = "Instant Estimate"
            ),
            CoreServiceDetailItem(
                title = "Car Finance",
                description = "Compare Personal Contract Purchase (PCP), Hire Purchase (HP) & loan options from UK lenders.",
                icon = Icons.Default.AccountBalance,
                color = Color(0xFF1976D2),
                route = "car_finance",
                badge = "Flexible Rates"
            ),
            CoreServiceDetailItem(
                title = "Car Insurance",
                description = "Compare insurance quotes from 100+ top UK providers. Save on comprehensive and third-party cover.",
                icon = Icons.Default.VerifiedUser,
                color = Color(0xFF388E3C),
                route = "car_insurance",
                badge = "Save Up To £290"
            ),
            CoreServiceDetailItem(
                title = "Vehicle History Check",
                description = "Free DVLA MOT history, mileage verification, road tax status & optional full HPI status checks.",
                icon = Icons.Default.Assignment,
                color = Color(0xFF0097A7),
                route = "vehicle_history",
                badge = "Free MOT Checker"
            ),
            CoreServiceDetailItem(
                title = "Breakdown Cover",
                description = "24/7 UK & European roadside assistance, home start & recovery with AA, RAC, Green Flag & partners.",
                icon = Icons.Default.LocalShipping,
                color = Color(0xFF7B1FA2),
                route = "breakdown_cover",
                badge = "24/7 Roadside"
            ),
            CoreServiceDetailItem(
                title = "Reviews & Car Guides",
                description = "In-depth used car reviews, buying guides, reliability ratings & expert UK automotive advice.",
                icon = Icons.Default.MenuBook,
                color = Color(0xFFE91E63),
                route = "reviews_guides",
                badge = "Expert Advice"
            )
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Core Services",
                subtitle = "Essential UK Automotive Services",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "core_services",
                onNavigate = onNavigateRoute
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateBackground),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                HeroBanner(
                    title = "Core Automotive Services",
                    subtitle = "Everything you need to buy, sell, finance, insure and check vehicles in the UK.",
                    ctaText = "Browse Used Cars",
                    badgeText = "All Services",
                    onCtaClick = { onNavigateRoute("buy_cars") }
                )
            }

            item {
                Text(
                    text = "ALL CORE SERVICES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
            }

            items(services) { item ->
                BlinkCard(
                    onClick = { onNavigateRoute(item.route) },
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = item.color.copy(alpha = 0.12f),
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = item.color,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = item.color.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = item.badge,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = item.color,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        maxLines = 1
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = item.description,
                                fontSize = 12.sp,
                                color = TextSecondaryLight,
                                lineHeight = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Navigate",
                            tint = item.color,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
