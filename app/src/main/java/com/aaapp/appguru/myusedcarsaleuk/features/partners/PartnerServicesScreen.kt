package com.aaapp.appguru.myusedcarsaleuk.features.partners

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

private data class PartnerCategory(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val route: String,
    val isExternal: Boolean = false,
    val partnerKey: String = route,
    val badge: String? = null
)

@Composable
fun PartnerServicesScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    var redirectingPartnerId by remember { mutableStateOf<String?>(null) }

    val categories = remember {

        listOf(
            PartnerCategory(
                title = "Tyres & Alloys",
                description = "Shop top tyre brands & alloy wheels on Amazon UK",
                icon = Icons.Default.TripOrigin,
                color = Color(0xFF512DA8),
                route = "amazon_tyres",
                isExternal = true,
                partnerKey = "amazon_tyres",
                badge = "Amazon Direct"
            ),
            PartnerCategory(
                title = "Accessories & Parts",
                description = "Car care, dash cams, wiper blades & auto spare parts",
                icon = Icons.Default.ShoppingCart,
                color = Color(0xFFC2185B),
                route = "amazon_accessories",
                isExternal = true,
                partnerKey = "amazon_accessories",
                badge = "Amazon Direct"
            ),
            PartnerCategory(
                title = "Car Finance",
                description = "Compare HP & PCP car loans from top UK lenders",
                icon = Icons.Default.AccountBalance,
                color = Color(0xFF1976D2),
                route = "car_finance",
                badge = "Instant Quotes"
            ),
            PartnerCategory(
                title = "Car Insurance",
                description = "Compare 100+ insurance deals & save money",
                icon = Icons.Default.VerifiedUser,
                color = Color(0xFF388E3C),
                route = "car_insurance",
                badge = "Save Up To £290"
            ),
            PartnerCategory(
                title = "Vehicle History",
                description = "Free MOT check, HPI status & mileage history",
                icon = Icons.Default.Assignment,
                color = Color(0xFF0097A7),
                route = "vehicle_history",
                badge = "Free MOT Tool"
            ),
            PartnerCategory(
                title = "Breakdown Cover",
                description = "24/7 Roadside assistance, AA, RAC & Green Flag",
                icon = Icons.Default.LocalShipping,
                color = Color(0xFFE65100),
                route = "breakdown_cover",
                badge = "Roadside 24/7"
            ),
            PartnerCategory(
                title = "Sell & Valuation",
                description = "Free instant valuation & sell to 5,000+ dealers",
                icon = Icons.Default.Sell,
                color = Color(0xFFD81B60),
                route = "sell_car",
                badge = "Best Prices"
            ),
            PartnerCategory(
                title = "Buy Used Cars",
                description = "Search 400,000+ verified vehicles across UK",
                icon = Icons.Default.DirectionsCar,
                color = Color(0xFF283593),
                route = "buy_cars",
                badge = "400k+ Listings"
            )
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Partner Services",
                subtitle = "Official UK Automotive Deals",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Hero Header
            item {
                HeroBanner(
                    title = "Automotive Partner Hub",
                    subtitle = "Explore verified partner services for tyres, accessories, finance, insurance, and vehicle checks.",
                    ctaText = "Browse Amazon Accessories",
                    badgeText = "Verified UK Partners",
                    isRedirecting = redirectingPartnerId == "hero_amazon",
                    onCtaClick = {
                        val url = ConfigManager.getResolvedUrl("amazon_accessories")
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "Amazon UK - Car Accessories & Parts",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        ) { loading -> 
                            redirectingPartnerId = if (loading) "hero_amazon" else null
                        }
                    }
                )
            }

            // Quick Category Cards
            item {
                Text(
                    text = "ALL PARTNER CATEGORIES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            items(categories) { category ->
                val isCurrentCategoryRedirecting = redirectingPartnerId == category.partnerKey
                BlinkCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (category.isExternal && redirectingPartnerId == null) {
                            val url = ConfigManager.getResolvedUrl(category.partnerKey)
                            ChromeTabManager.launchUrl(
                                context = context,
                                url = url,
                                title = "${category.title} - UK Automotive",
                                imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                            ) { loading -> 
                                redirectingPartnerId = if (loading) category.partnerKey else null
                            }
                        } else if (!category.isExternal) {
                            onNavigateRoute(category.route)
                        }
                    },
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
                        Surface(
                            shape = CircleShape,
                            color = category.color.copy(alpha = 0.12f),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (isCurrentCategoryRedirecting && category.isExternal) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = category.color,
                                        strokeWidth = 2.5.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = category.icon,
                                        contentDescription = category.title,
                                        tint = category.color,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = category.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                category.badge?.let { badge ->
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = category.color.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = badge,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = category.color,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = category.description,
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
                            tint = RoyalBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Featured Direct Affiliate Deals
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "FEATURED PARTNER DEALS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            item {
                PartnerCard(
                    name = "Amazon UK - Tyres & Alloys",
                    rating = 4.8,
                    reviewsCount = "12,400+",
                    badge = "Official Direct Partner",
                    description = "Find premium tyre brands (Michelin, Pirelli, Continental), alloy wheels, tyre pressure monitoring systems, and fitting kits on Amazon UK.",
                    benefits = listOf("Top Tyre Brands", "Alloy Wheels & Trims", "Fast UK Delivery", "Fitment Guarantee"),
                    ctaText = "Shop Tyres & Alloys on Amazon",
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl("amazon_tyres")
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "Amazon UK - Car Tyres & Alloys",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )
            }

            item {
                PartnerCard(
                    name = "Amazon UK - Auto Accessories & Parts",
                    rating = 4.9,
                    reviewsCount = "25,000+",
                    badge = "Official Direct Partner",
                    description = "Browse car care supplies, dash cams, floor mats, seat covers, wiper blades, oil, batteries, and essential car replacement parts.",
                    benefits = listOf("Dash Cams & Electronics", "Car Detailing & Care", "Essential Replacement Parts", "Prime Delivery Options"),
                    ctaText = "Shop Accessories on Amazon",
                    onContinueClick = {
                        val url = ConfigManager.getResolvedUrl("amazon_accessories")
                        ChromeTabManager.launchUrl(
                            context = context,
                            url = url,
                            title = "Amazon UK - Car Accessories & Parts",
                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                        )
                    }
                )
            }


            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
