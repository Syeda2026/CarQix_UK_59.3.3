package com.aaapp.appguru.myusedcarsaleuk.features.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aaapp.appguru.myusedcarsaleuk.R
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdManager
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val savedItems by viewModel.savedItems.collectAsStateWithLifecycle()
    var showExitRatingDialog by remember { mutableStateOf(false) }
    var redirectingPartnerId by remember { mutableStateOf<String?>(null) }

    // Intercept back button on Home Screen to show rating & exit dialog
    BackHandler {
        showExitRatingDialog = true
    }

    if (showExitRatingDialog) {
        ExitRatingDialog(
            onDismiss = { showExitRatingDialog = false },
            onExit = {
                showExitRatingDialog = false
                (context as? Activity)?.finish()
            },
            onRate = {
                showExitRatingDialog = false
                try {
                    val packageName = context.packageName
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "CarQix UK",
                onMenuClick = { onNavigateRoute("more") },
                onNotificationClick = { onNavigateRoute("more") },
                notificationCount = 3,
                onSearchClick = { onNavigateRoute("global_search") },
                onSavedClick = { onNavigateRoute("saved") },
                savedCount = savedItems.size
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



    // Hero Banner Slider (5 Major Services)
    item {
        val heroSlides = remember {
            listOf(
                HeroSlideData(
                    title = "Find Your Perfect Car",
                    subtitle = "Search 20+ UK marketplaces like AutoTrader, Motors & eBay in one place.",
                    badgeText = "20+ UK Marketplaces",
                    ctaText = "Start Searching",
                    route = "buy_cars",
                    gradientColors = listOf(Color(0xFF0F172A), Color(0xFF1E388E)),
                    buttonColor = RoyalBlue,
                    badgeBgColor = AccentGold,
                    badgeTextColor = NavyDark
                ),
                HeroSlideData(
                    title = "Sell Your Car Fast",
                    subtitle = "Get top cash offers from Motorway, Webuyanycar & Carwow in minutes.",
                    badgeText = "Best Cash Offers",
                    ctaText = "Get Free Quote",
                    route = "sell_car",
                    gradientColors = listOf(Color(0xFF064E3B), Color(0xFF047857)),
                    buttonColor = SuccessGreen,
                    badgeBgColor = Color(0xFFA7F3D0),
                    badgeTextColor = Color(0xFF064E3B)
                ),
                HeroSlideData(
                    title = "Free Market Valuation",
                    subtitle = "Check live trade-in, private sale & dealer valuation reports for free.",
                    badgeText = "100% Free & Instant",
                    ctaText = "Check Value Now",
                    route = "value_car",
                    gradientColors = listOf(Color(0xFF451A03), Color(0xFFB45309)),
                    buttonColor = AccentGoldDark,
                    badgeBgColor = Color(0xFFFEF3C7),
                    badgeTextColor = Color(0xFF78350F)
                ),
                HeroSlideData(
                    title = "Low APR Car Finance",
                    subtitle = "Compare tailored finance rates & check approval with zero credit impact.",
                    badgeText = "Instant Eligibility",
                    ctaText = "Calculate Finance",
                    route = "car_finance",
                    gradientColors = listOf(Color(0xFF3B0764), Color(0xFF6B21A8)),
                    buttonColor = Color(0xFF9333EA),
                    badgeBgColor = Color(0xFFF3E8FF),
                    badgeTextColor = Color(0xFF581C87)
                ),
                HeroSlideData(
                    title = "Full Vehicle History",
                    subtitle = "Check HPI, DVLA MOT history, mileage anomalies & outstanding finance.",
                    badgeText = "Official DVLA Data",
                    ctaText = "Check History",
                    route = "vehicle_history",
                    gradientColors = listOf(Color(0xFF083344), Color(0xFF0E7490)),
                    buttonColor = Color(0xFF0EA5E9),
                    badgeBgColor = Color(0xFFCFFAFE),
                    badgeTextColor = Color(0xFF164E63)
                )
            )
        }

        HeroBannerSlider(
            slides = heroSlides,
            onSlideClick = { route -> onNavigateRoute(route) },
            isRedirecting = redirectingPartnerId != null
        )
    }

    // CORE SERVICES Section Header & Cards
    item {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CORE SERVICES",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    letterSpacing = 0.5.sp
                )
                BlinkTextButton(
                    onClick = { onNavigateRoute("core_services") },
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

            val coreServices = remember {
                listOf(
                    CoreServiceData("Buy Used Cars", Icons.Default.DirectionsCar, RoyalBlue, "buy_cars"),
                    CoreServiceData("Sell Your Car", Icons.Default.Sell, SuccessGreen, "sell_car"),
                    CoreServiceData("Value My Car", Icons.Default.TrendingUp, AccentGoldDark, "value_car"),
                    CoreServiceData("Car Finance", Icons.Default.CreditCard, Color(0xFF8E24AA), "car_finance"),
                    CoreServiceData("Car Insurance", Icons.Default.Shield, RoyalBlue, "car_insurance"),
                    CoreServiceData("Vehicle History", Icons.Default.FactCheck, SuccessGreen, "vehicle_history"),
                    CoreServiceData("Breakdown Cover", Icons.Default.Build, AccentGoldDark, "breakdown_cover"),
                    CoreServiceData("Reviews & Guides", Icons.Default.MenuBook, Color(0xFFE91E63), "reviews_guides")
                )
            }

            BoxWithConstraints {
                if (maxWidth >= 600.dp) {
                    val coreRows = remember(coreServices) { coreServices.chunked(4) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        coreRows.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { service ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        CoreServiceCard(
                                            data = service,
                                            onClick = { onNavigateRoute(service.route) }
                                        )
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
                    val columns = remember(coreServices) { coreServices.chunked(2) }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(columns) { pair ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.width(104.dp)
                            ) {
                                pair.forEach { service ->
                                    CoreServiceCard(
                                        data = service,
                                        onClick = { onNavigateRoute(service.route) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // SMART TOOLS Section Header & Cards
    item {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SMART TOOLS",
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

            val smartTools = remember {
                listOf(
                    Triple("buying_advice", "Buying Advice", Icons.Default.Lightbulb),
                    Triple("loan", "Finance Calc", Icons.Default.Calculate),
                    Triple("road_tax", "Road Tax", Icons.Default.ConfirmationNumber),
                    Triple("fuel_cost", "Fuel Cost", Icons.Default.LocalGasStation),
                    Triple("mileage", "Mileage Calc", Icons.Default.Speed),
                    Triple("depreciation", "Depreciation", Icons.Default.TrendingDown),
                    Triple("running_cost", "Running Cost", Icons.Default.AccountBalanceWallet)
                )
            }

            BoxWithConstraints {
                if (maxWidth >= 600.dp) {
                    val toolRows = remember(smartTools) { smartTools.chunked(4) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        toolRows.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { (id, name, icon) ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        BlinkCard(
                                            onClick = {
                                                if (id == "buying_advice") {
                                                    onNavigateRoute("buying_advice")
                                                } else {
                                                    onNavigateRoute("calculator_detail/$id")
                                                }
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
                                                    color = if (id == "buying_advice") AccentGold.copy(alpha = 0.2f) else RoyalBlue.copy(alpha = 0.12f),
                                                    shape = CircleShape,
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(
                                                            imageVector = icon,
                                                            contentDescription = name,
                                                            tint = if (id == "buying_advice") AccentGoldDark else RoyalBlue,
                                                            modifier = Modifier.size(17.dp)
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(
                                                    text = name,
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
                        items(smartTools) { (id, name, icon) ->
                            BlinkCard(
                                onClick = {
                                    if (id == "buying_advice") {
                                        onNavigateRoute("buying_advice")
                                    } else {
                                        onNavigateRoute("calculator_detail/$id")
                                    }
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
                                        color = if (id == "buying_advice") AccentGold.copy(alpha = 0.2f) else RoyalBlue.copy(alpha = 0.12f),
                                        shape = CircleShape,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = name,
                                                tint = if (id == "buying_advice") AccentGoldDark else RoyalBlue,
                                                modifier = Modifier.size(17.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = name,
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

    // PARTNER SERVICES Section Header & Cards
    item {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PARTNER SERVICES",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    letterSpacing = 0.5.sp
                )
                BlinkTextButton(
                    onClick = { onNavigateRoute("partner_services") },
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

            val partnerServices = remember {
                listOf(
                    PartnerServiceData("Finance Partners", Icons.Default.AccountBalance, Color(0xFF1976D2), "car_finance"),
                    PartnerServiceData("Insurance Partners", Icons.Default.VerifiedUser, Color(0xFF388E3C), "car_insurance"),
                    PartnerServiceData("Vehicle History", Icons.Default.Assignment, Color(0xFF0097A7), "vehicle_history"),
                    PartnerServiceData("Breakdown Cover", Icons.Default.LocalShipping, Color(0xFFE65100), "breakdown_cover"),
                    PartnerServiceData("Tyres & Alloys", Icons.Default.TripOrigin, Color(0xFF512DA8), "smart_tools", isExternal = true, partnerKey = "amazon_tyres"),
                    PartnerServiceData("Parts & Accessories", Icons.Default.ShoppingCart, Color(0xFFC2185B), "smart_tools", isExternal = true, partnerKey = "amazon_accessories")
                )
            }

            BoxWithConstraints {
                if (maxWidth >= 600.dp) {
                    val partnerRows = remember(partnerServices) { partnerServices.chunked(3) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        partnerRows.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { partner ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        BlinkCard(
                                            onClick = {
                                                if (partner.isExternal && redirectingPartnerId == null) {
                                                    val url = ConfigManager.getResolvedUrl(partner.partnerKey)
                                                    ChromeTabManager.launchUrl(
                                                        context = context,
                                                        url = url,
                                                        title = "${partner.title} - UK Car Services",
                                                        imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                                    ) { loading -> 
                                                        redirectingPartnerId = if (loading) partner.partnerKey else null
                                                    }
                                                } else if (!partner.isExternal) {
                                                    onNavigateRoute(partner.route)
                                                }
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
                                                if (redirectingPartnerId == partner.partnerKey && partner.isExternal) {
                                                    CircularProgressIndicator(
                                                        modifier = Modifier.size(24.dp),
                                                        color = partner.color,
                                                        strokeWidth = 2.5.dp
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Text(
                                                        text = "Opening...",
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = NavyDark
                                                    )
                                                } else {
                                                    Surface(
                                                        color = partner.color.copy(alpha = 0.12f),
                                                        shape = CircleShape,
                                                        modifier = Modifier.size(32.dp)
                                                    ) {
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Icon(
                                                                imageVector = partner.icon,
                                                                contentDescription = partner.title,
                                                                tint = partner.color,
                                                                modifier = Modifier.size(17.dp)
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                    Text(
                                                        text = partner.title,
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
                                if (rowItems.size < 3) {
                                    repeat(3 - rowItems.size) {
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
                        items(partnerServices) { partner ->
                            BlinkCard(
                                onClick = {
                                    if (partner.isExternal && redirectingPartnerId == null) {
                                        val url = ConfigManager.getResolvedUrl(partner.partnerKey)
                                        ChromeTabManager.launchUrl(
                                            context = context,
                                            url = url,
                                            title = "${partner.title} - UK Car Services",
                                            imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                        ) { loading -> 
                                            redirectingPartnerId = if (loading) partner.partnerKey else null
                                        }
                                    } else if (!partner.isExternal) {
                                        onNavigateRoute(partner.route)
                                    }
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
                                    if (redirectingPartnerId == partner.partnerKey && partner.isExternal) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = partner.color,
                                            strokeWidth = 2.5.dp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Opening...",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark
                                        )
                                    } else {
                                        Surface(
                                            color = partner.color.copy(alpha = 0.12f),
                                            shape = CircleShape,
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = partner.icon,
                                                    contentDescription = partner.title,
                                                    tint = partner.color,
                                                    modifier = Modifier.size(17.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = partner.title,
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
    }

    // SAVED Section Header & Cards
    item {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SAVED",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    letterSpacing = 0.5.sp
                )
                BlinkTextButton(
                    onClick = { onNavigateRoute("saved") },
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

            val savedCategories = remember {
                listOf(
                    SavedCategoryData("Saved Searches", "3 saved", Icons.Default.Favorite, Color(0xFFE91E63), Color(0xFFE91E63).copy(alpha = 0.12f)),
                    SavedCategoryData("Favourites", "12 items", Icons.Default.Bookmark, Color(0xFF673AB7), Color(0xFF673AB7).copy(alpha = 0.12f)),
                    SavedCategoryData("Recently Viewed", "8 vehicles", Icons.Default.Schedule, Color(0xFF00897B), Color(0xFF00897B).copy(alpha = 0.12f)),
                    SavedCategoryData("My Vehicles", "2 vehicles", Icons.Default.DirectionsCar, Color(0xFFF57C00), Color(0xFFF57C00).copy(alpha = 0.12f))
                )
            }

            BoxWithConstraints {
                if (maxWidth >= 600.dp) {
                    val savedRows = remember(savedCategories) { savedCategories.chunked(2) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        savedRows.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { cat ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        BlinkCard(
                                            onClick = { onNavigateRoute("saved") },
                                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Surface(
                                                    color = cat.bgColor,
                                                    shape = CircleShape,
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(
                                                            imageVector = cat.icon,
                                                            contentDescription = cat.title,
                                                            tint = cat.iconColor,
                                                            modifier = Modifier.size(17.dp)
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = cat.title,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = NavyDark,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Text(
                                                        text = cat.subtitle,
                                                        fontSize = 9.sp,
                                                        color = TextSecondaryLight
                                                    )
                                                }
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                    contentDescription = null,
                                                    tint = TextSecondaryLight,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                if (rowItems.size < 2) {
                                    repeat(2 - rowItems.size) {
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
                        items(savedCategories) { cat ->
                            BlinkCard(
                                onClick = { onNavigateRoute("saved") },
                                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(142.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = cat.bgColor,
                                        shape = CircleShape,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = cat.icon,
                                                contentDescription = cat.title,
                                                tint = cat.iconColor,
                                                modifier = Modifier.size(17.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = cat.title,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = cat.subtitle,
                                            fontSize = 9.sp,
                                            color = TextSecondaryLight
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = TextSecondaryLight,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

            // Sponsored Native Ad (Below Saved Section)
            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isHomeNativeAdEnabled
                )
            }

            // Finance Promo Hero Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                color = AccentGold,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(bottom = 6.dp)
                            ) {
                                Text(
                                    text = "FAST • SECURE • TRUSTED",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = NavyDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Need Car Finance?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Get pre-approved in minutes with soft search • No impact on credit score",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                lineHeight = 14.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            BlinkButton(
                                onClick = { onNavigateRoute("car_finance") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RoyalBlue,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(text = "Check Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = AccentGold.copy(alpha = 0.85f),
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

// Data class for Core Services
private data class CoreServiceData(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

// Data class for Partner Services
private data class PartnerServiceData(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val route: String,
    val isExternal: Boolean = false,
    val partnerKey: String = route
)

// Data class for Saved Categories
private data class SavedCategoryData(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color,
    val bgColor: Color
)

@Composable
private fun QuickStatItem(value: String, label: String, icon: ImageVector) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.width(82.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = RoyalBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = NavyDark
            )
            Text(
                text = label,
                fontSize = 9.sp,
                color = TextSecondaryLight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CoreServiceCard(
    data: CoreServiceData,
    onClick: () -> Unit
) {
    BlinkCard(
        onClick = onClick,
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
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = data.color.copy(alpha = 0.12f),
                shape = CircleShape,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = data.title,
                        tint = data.color,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = data.title,
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

@Composable
private fun ExitRatingDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    onRate: () -> Unit
) {
    var selectedRating by remember { mutableStateOf(5) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = SurfaceWhite,
        icon = {
            Surface(
                color = AccentGold.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rate App",
                        tint = AccentGoldDark,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        title = {
            Text(
                text = "Enjoying CarQix UK?",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = NavyDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Are you sure you want to leave?\nYour feedback helps us make UK car buying and smart tools even better!",
                    fontSize = 13.5.sp,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Interactive 5-Star Row
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    (1..5).forEach { index ->
                        IconButton(
                            onClick = { selectedRating = index },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (index <= selectedRating) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "$index Stars",
                                tint = if (index <= selectedRating) AccentGoldDark else Color(0xFFCBD5E1),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRate,
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rate This App",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Later",
                            color = NavyDark,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    OutlinedButton(
                        onClick = onExit,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ErrorRed
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Exit",
                            color = ErrorRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        },
        dismissButton = null
    )
}

