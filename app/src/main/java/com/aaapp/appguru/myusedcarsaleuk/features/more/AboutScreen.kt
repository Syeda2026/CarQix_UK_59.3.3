package com.aaapp.appguru.myusedcarsaleuk.features.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun AboutScreen(
    onNavigateRoute: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "About CarQix UK",
                subtitle = "Your Complete UK Automotive Companion",
                showBackButton = true,
                onBackClick = { onNavigateRoute("more") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "more",
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Overview Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = RoyalBlue.copy(alpha = 0.12f), shape = CircleShape, modifier = Modifier.size(40.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(22.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = ConfigManager.appConfig.appName, fontWeight = FontWeight.Black, fontSize = 17.sp, color = NavyDark)
                                Text(text = "Your Trusted Automotive Companion", fontSize = 12.sp, color = TextSecondaryLight)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "CarQix UK is designed to empower UK motorists with all the tools needed to search, evaluate, and purchase used vehicles confidently.",
                            fontSize = 12.5.sp,
                            color = TextPrimaryLight,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Whether you're shopping for your next vehicle, calculating finance rates and road tax, checking official MOT history, or seeking buying advice, our app connects you with trusted resources in one convenient place.",
                            fontSize = 12.sp,
                            color = TextSecondaryLight,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            // App Brand Logo Showcase Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Official Brand & App Icon",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        CarQixPlayStoreLogo(size = 150.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "CarQix UK • Official 512×512 Store Asset",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondaryLight
                        )
                    }
                }
            }

            // Key Features Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "What You Can Do", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(10.dp))
                        AboutFeatureRow(Icons.Default.Search, "Marketplace Search", "Browse leading UK car marketplaces like AutoTrader, Gumtree, and eBay Motors.")
                        AboutFeatureRow(Icons.Default.Calculate, "Smart Calculators", "Calculate car finance monthly payments, official road tax (VED), and fuel costs.")
                        AboutFeatureRow(Icons.Default.Verified, "Vehicle History & MOT", "Check official DVLA MOT history, mileage logs, and recall records.")
                        AboutFeatureRow(Icons.Default.Lightbulb, "Expert Buying Advice", "10-point inspection checklists, legal rights under Consumer Rights Act 2015, and negotiation guides.")
                    }
                }
            }

            // App Specs Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "App Information", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "• Version: v${ConfigManager.appConfig.appVersion}", fontSize = 12.sp, color = TextPrimaryLight)
                        Text(text = "• Support: ${ConfigManager.appConfig.supportEmail}", fontSize = 12.sp, color = TextPrimaryLight)
                        Text(text = "• Region: United Kingdom (UK)", fontSize = 12.sp, color = TextPrimaryLight)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun AboutFeatureRow(icon: ImageVector, title: String, desc: String) {
    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
        Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark)
            Text(text = desc, fontSize = 11.5.sp, color = TextSecondaryLight, lineHeight = 15.sp)
        }
    }
}

