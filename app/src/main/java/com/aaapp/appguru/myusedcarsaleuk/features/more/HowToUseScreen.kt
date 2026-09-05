package com.aaapp.appguru.myusedcarsaleuk.features.more

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun HowToUseScreen(
    onNavigateRoute: (String) -> Unit
) {
    var expandedStep by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "How to Use It",
                subtitle = "User Guide & Step-by-Step Walkthrough",
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
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Hero Guide Banner Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = NavyDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = AccentGold,
                                shape = CircleShape,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = NavyDark,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "CarQix UK User Manual",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Master all features in 7 simple steps",
                                    fontSize = 11.5.sp,
                                    color = Color.White.copy(0.85f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Learn how to search across UK car marketplaces, bookmark vehicles directly via the custom tab heart button, check official MOT history, and calculate monthly finance with ease.",
                            fontSize = 12.5.sp,
                            color = Color.White.copy(0.9f),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Quick Workflow Pills Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "QUICK OVERVIEW WORKFLOW",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WorkflowStepPill(number = "1", title = "Search", icon = Icons.Default.Search)
                            WorkflowStepPill(number = "2", title = "Heart / Save", icon = Icons.Default.Favorite)
                            WorkflowStepPill(number = "3", title = "MOT Check", icon = Icons.Default.Verified)
                            WorkflowStepPill(number = "4", title = "Calculate", icon = Icons.Default.Calculate)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "STEP-BY-STEP INSTRUCTIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            // Step 1: Searching Used Cars
            item {
                GuideStepCard(
                    stepNumber = "1",
                    title = "Search & Browse Used Cars",
                    subtitle = "AutoTrader, Motors, Gumtree & eBay",
                    icon = Icons.Default.Search,
                    isExpanded = expandedStep == 1 || expandedStep == 0,
                    onToggle = { expandedStep = if (expandedStep == 1) -1 else 1 },
                    bullets = listOf(
                        "Tap the **Buy Cars** tab from the bottom navigation or the **Search Cars** card on the Home screen.",
                        "Filter by body style (Hatchback, SUV, Saloon, Estate), fuel type (Petrol, Diesel, Hybrid, Electric), or price range.",
                        "Tap any portal button to open leading UK platforms like AutoTrader, Motors.co.uk, Gumtree, or Carwow inside the high-speed in-app browser."
                    )
                )
            }

            // Step 2: Saving / Favoriting Cars
            item {
                GuideStepCard(
                    stepNumber = "2",
                    title = "Save Any Listing (Heart Icon ❤️)",
                    subtitle = "Instant bookmarking with scraped photo & title",
                    icon = Icons.Default.Favorite,
                    badge = "KEY FEATURE",
                    isExpanded = expandedStep == 2 || expandedStep == 0,
                    onToggle = { expandedStep = if (expandedStep == 2) -1 else 2 },
                    bullets = listOf(
                        "While viewing any car listing on any website in the in-app browser, look at the top-right toolbar.",
                        "Tap the ❤️ Heart Icon (or select 'Save to Favorites' from the menu).",
                        "The app automatically captures the exact car deep link, parses the car title, and scrapes the live vehicle photograph.",
                        "A toast notification confirming '❤️ Saved: [Car Title]' will appear.",
                        "View, manage, or revisit all your bookmarked cars anytime under the bottom Saved tab."
                    )
                )
            }

            // Step 3: MOT & Vehicle History Check
            item {
                GuideStepCard(
                    stepNumber = "3",
                    title = "Check MOT History & Mileage",
                    subtitle = "Official DVSA & DVLA verification",
                    icon = Icons.Default.Verified,
                    isExpanded = expandedStep == 3 || expandedStep == 0,
                    onToggle = { expandedStep = if (expandedStep == 3) -1 else 3 },
                    bullets = listOf(
                        "Navigate to **Vehicle History** from the Home dashboard.",
                        "Enter the UK vehicle registration mark (e.g. `AB12 CDE`) into the Reg Box.",
                        "Review historical MOT passes, failures, and advisory notices.",
                        "Verify mileage records to prevent purchasing clocked vehicles.",
                        "Check tax status, expiry dates, and official safety recall alerts."
                    )
                )
            }

            // Step 4: Financial Calculators
            item {
                GuideStepCard(
                    stepNumber = "4",
                    title = "Calculate Finance, Tax & Running Costs",
                    subtitle = "HP, PCP, Road Tax (VED) & Fuel",
                    icon = Icons.Default.Calculate,
                    isExpanded = expandedStep == 4 || expandedStep == 0,
                    onToggle = { expandedStep = if (expandedStep == 4) -1 else 4 },
                    bullets = listOf(
                        "Open **Smart Tools & Calculators** from the Core Services section.",
                        "**Car Finance Calculator**: Adjust vehicle price, deposit, loan term, and APR% to estimate exact monthly payments.",
                        "**Road Tax (VED) Tool**: Estimate annual vehicle tax rates for petrol, diesel, and alternative fuel vehicles.",
                        "**Fuel Cost Calculator**: Enter mileage and MPG to compute realistic weekly and monthly commute costs."
                    )
                )
            }

            // Step 5: Valuation & Selling
            item {
                GuideStepCard(
                    stepNumber = "5",
                    title = "Value & Sell Your Current Vehicle",
                    subtitle = "Get free valuations from top UK car buyers",
                    icon = Icons.Default.PriceCheck,
                    isExpanded = expandedStep == 5,
                    onToggle = { expandedStep = if (expandedStep == 5) -1 else 5 },
                    bullets = listOf(
                        "Go to **Value My Car** or **Sell Your Car** on the Home screen.",
                        "Compare instant quotes from trusted UK buyers including Motorway, WeBuyAnyCar, and SellMyCar.",
                        "Understand true market trade-in vs private sale pricing before agreeing to a deal."
                    )
                )
            }

            // Step 6: Insurance & Breakdown Cover
            item {
                GuideStepCard(
                    stepNumber = "6",
                    title = "Insurance & Breakdown Roadside Cover",
                    subtitle = "Protect your vehicle before driving home",
                    icon = Icons.Default.Shield,
                    isExpanded = expandedStep == 6,
                    onToggle = { expandedStep = if (expandedStep == 6) -1 else 6 },
                    bullets = listOf(
                        "Visit **Car Insurance** or **Breakdown Cover** under the Services menu.",
                        "Compare competitive quotes across top UK price comparison sites.",
                        "Set up temporary drive-away insurance or roadside breakdown cover (RAC, AA, Green Flag) before collecting your newly bought vehicle."
                    )
                )
            }

            // Step 7: Inspection & Buying Advice
            item {
                GuideStepCard(
                    stepNumber = "7",
                    title = "10-Point Pre-Purchase Inspection",
                    subtitle = "Legal rights & on-site checklist",
                    icon = Icons.Default.Checklist,
                    isExpanded = expandedStep == 7,
                    onToggle = { expandedStep = if (expandedStep == 7) -1 else 7 },
                    bullets = listOf(
                        "Access the **Buying Advice & Inspection Guide** before meeting the seller.",
                        "Use the 10-point checklist: Check the V5C logbook watermark, verify VIN numbers match the chassis and glass, test all electronics, and inspect cold engine starts.",
                        "Review your statutory consumer rights under the Consumer Rights Act 2015 for dealer purchases."
                    )
                )
            }

            // Quick Navigation Shortcuts
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "READY TO GET STARTED?",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onNavigateRoute("buy_cars") },
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Buy Cars", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { onNavigateRoute("saved") },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = RoyalBlue),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Saved Cars", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun WorkflowStepPill(number: String, title: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Surface(
            color = RoyalBlue.copy(alpha = 0.12f),
            shape = CircleShape,
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Step $number",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AccentGoldDark
        )
        Text(
            text = title,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = NavyDark
        )
    }
}

@Composable
private fun GuideStepCard(
    stepNumber: String,
    title: String,
    subtitle: String,
    icon: ImageVector,
    badge: String? = null,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    bullets: List<String>
) {
    BlinkCard(
        onClick = onToggle,
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = RoyalBlue,
                    shape = CircleShape,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = stepNumber,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = NavyDark,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (badge != null) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = AccentGold,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = badge,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = NavyDark,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 11.5.sp,
                        color = TextSecondaryLight,
                        lineHeight = 15.sp
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 4.dp, end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalDivider(color = SlateBackground, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(2.dp))
                    bullets.forEach { bullet ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "•",
                                color = RoyalBlue,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .width(16.dp)
                                    .padding(top = 1.dp)
                            )
                            val cleanText = bullet.replace("**", "")
                            Text(
                                text = cleanText,
                                fontSize = 12.sp,
                                color = TextPrimaryLight,
                                lineHeight = 17.5.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
