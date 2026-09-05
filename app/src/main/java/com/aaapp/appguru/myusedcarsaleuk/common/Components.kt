package com.aaapp.appguru.myusedcarsaleuk.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.aaapp.appguru.myusedcarsaleuk.R
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "CarQix UK",
    subtitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onMenuClick: (() -> Unit)? = null,
    onNotificationClick: () -> Unit = {},
    notificationCount: Int = 3,
    onSearchClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null,
    onSavedClick: (() -> Unit)? = null,
    savedCount: Int = 0,
    onNavigateRoute: ((String) -> Unit)? = null
) {
    val actualSearchClick = onSearchClick ?: onNavigateRoute?.let { navigate -> { navigate("global_search") } }
    val actualMoreClick = onMoreClick ?: onNavigateRoute?.let { navigate -> { navigate("more") } }

    Surface(
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        TopAppBar(
            title = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (title.endsWith("UK")) {
                            val mainTitle = title.substringBeforeLast("UK").trim()
                            Text(
                                text = mainTitle,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                letterSpacing = (-0.3).sp,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "UK",
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                letterSpacing = (-0.3).sp,
                                color = ErrorRed
                            )
                        } else {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Black,
                                fontSize = 17.sp,
                                letterSpacing = (-0.3).sp,
                                color = NavyDark,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (showBackButton) {
                    BlinkIconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NavyDark
                        )
                    }
                } else if (onMenuClick != null) {
                    BlinkIconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = NavyDark
                        )
                    }
                }
            },
            actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    if (actualSearchClick != null) {
                        BlinkIconButton(onClick = actualSearchClick) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search",
                                tint = NavyDark
                            )
                        }
                    }

                    if (onSavedClick != null) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            BlinkIconButton(onClick = onSavedClick) {
                                Icon(
                                    imageVector = if (savedCount > 0) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Saved Items",
                                    tint = if (savedCount > 0) RoyalBlue else NavyDark
                                )
                            }
                            if (savedCount > 0) {
                                Surface(
                                    color = ErrorRed,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 4.dp, end = 4.dp)
                                        .size(16.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = if (savedCount > 99) "99+" else savedCount.toString(),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (actualMoreClick != null) {
                        BlinkIconButton(onClick = actualMoreClick) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More & Settings",
                                tint = NavyDark
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SurfaceWhite,
                titleContentColor = NavyDark
            )
        )
    }
}

@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    showBannerAd: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceWhite)
    ) {
        if (showBannerAd) {
            AdMobBannerSlot()
        }
        Surface(
            color = SurfaceWhite,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val items = listOf(
                    BottomNavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
                    BottomNavItem("global_search", "Search", Icons.Filled.Search, Icons.Outlined.Search),
                    BottomNavItem("smart_tools", "Tools", Icons.Filled.Build, Icons.Outlined.Build),
                    BottomNavItem("saved", "Saved", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
                    BottomNavItem("more", "More", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz)
                )

                items.forEach { item ->
                    val selected = currentRoute == item.route
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) RoyalBlue.copy(alpha = 0.12f) else Color.Transparent)
                            .blinkClickable { onNavigate(item.route) }
                            .padding(vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            tint = if (selected) RoyalBlue else TextSecondaryLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected) RoyalBlue else TextSecondaryLight,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

private data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

data class HeroSlideData(
    val title: String,
    val subtitle: String,
    val badgeText: String,
    val ctaText: String,
    val route: String,
    val gradientColors: List<Color>,
    val buttonColor: Color,
    val badgeBgColor: Color = AccentGold,
    val badgeTextColor: Color = NavyDark
)

@Composable
fun HeroBannerSlider(
    slides: List<HeroSlideData>,
    onSlideClick: (String) -> Unit,
    isRedirecting: Boolean = false
) {
    if (slides.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { slides.size })
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll timer every 3 seconds continuously
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (!pagerState.isScrollInProgress && !isRedirecting) {
                val nextPage = (pagerState.currentPage + 1) % slides.size
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = !isRedirecting
        ) { page ->
            val slide = slides[page]
            BlinkCard(
                onClick = { if (!isRedirecting) onSlideClick(slide.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                ) {
                    // Background Car Image
                    Image(
                        painter = painterResource(id = R.drawable.ic_car_hero_bg),
                        contentDescription = "Car Banner Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient Scrim overlay tailored to each slide theme
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        slide.gradientColors[0].copy(alpha = 0.90f),
                                        slide.gradientColors.getOrElse(1) { slide.gradientColors[0] }.copy(alpha = 0.60f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Content Column
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.fillMaxWidth(0.80f)) {
                            Surface(
                                color = slide.badgeBgColor,
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = slide.badgeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = slide.badgeTextColor,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = slide.title,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = slide.subtitle,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.92f),
                                lineHeight = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        BlinkButton(
                            onClick = { onSlideClick(slide.route) },
                            enabled = !isRedirecting,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = slide.buttonColor,
                                contentColor = Color.White,
                                disabledContainerColor = slide.buttonColor,
                                disabledContentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (isRedirecting) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Opening...", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            } else {
                                Text(text = slide.ctaText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Clickable Page Indicator Dots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(slides.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 7.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) RoyalBlue else Color(0xFFCBD5E1))
                        .blinkClickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = index,
                                    animationSpec = tween(
                                        durationMillis = 800,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun HeroBanner(
    title: String,
    subtitle: String,
    ctaText: String = "Explore Now",
    onCtaClick: () -> Unit,
    badgeText: String? = null,
    isRedirecting: Boolean = false
) {
    BlinkCard(
        onClick = { if (!isRedirecting) onCtaClick() },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            // Background Car Image
            Image(
                painter = painterResource(id = R.drawable.ic_car_hero_bg),
                contentDescription = "Car Banner Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Scrim for readable overlay text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.85f),
                                Color.Black.copy(alpha = 0.45f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Content Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                    if (badgeText != null) {
                        Surface(
                            color = AccentGold,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = badgeText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Button(
                    onClick = onCtaClick,
                    enabled = !isRedirecting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RoyalBlue,
                        contentColor = Color.White,
                        disabledContainerColor = RoyalBlue,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (isRedirecting) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Opening...", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    } else {
                        Text(text = ctaText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdMobBannerSlot(
    modifier: Modifier = Modifier,
    adUnitId: String = ConfigManager.adsConfig.bannerAdUnitId
) {
    val adsConfig = ConfigManager.adsConfig
    val primaryBannerId = adUnitId.ifBlank { adsConfig.bannerAdUnitId }

    if (adsConfig.isAdsEnabled && adsConfig.isBannerAdsEnabled) {
        val isInspection = LocalInspectionMode.current
        var isAdLoaded by remember(primaryBannerId) { mutableStateOf(false) }
        var shouldStartLoading by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // Delay banner loading slightly to prioritize UI rendering
            delay(800)
            shouldStartLoading = true
        }

        if (isInspection) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(vertical = 4.dp),
                color = Color.Transparent
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Google AdMob Banner ($primaryBannerId)", fontSize = 11.sp, color = NavyDark)
                    }
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .then(if (isAdLoaded && shouldStartLoading) Modifier.wrapContentHeight() else Modifier.height(0.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (shouldStartLoading) {
                    key(primaryBannerId) {
                        AndroidView(
                            modifier = if (isAdLoaded) {
                                Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(vertical = 4.dp)
                            } else {
                                Modifier.size(0.dp)
                            },
                            factory = { context ->
                                AdView(context).apply {
                                    setAdSize(AdSize.BANNER)
                                    setAdUnitId(primaryBannerId)
                                    adListener = object : AdListener() {
                                        override fun onAdLoaded() {
                                            super.onAdLoaded()
                                            isAdLoaded = true
                                        }

                                        override fun onAdFailedToLoad(adError: LoadAdError) {
                                            super.onAdFailedToLoad(adError)
                                            android.util.Log.w(
                                                "AdMobBannerSlot",
                                                "Banner failed to load (unit: $primaryBannerId): code=${adError.code}, msg=${adError.message}"
                                            )
                                            isAdLoaded = false
                                        }
                                    }
                                    loadAd(AdRequest.Builder().build())
                                }
                            },
                            update = { /* AdView ID cannot be changed once set; handled by key(activeBannerId) */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrustBadge(text: String, icon: ImageVector = Icons.Default.Verified) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(RoyalBlue.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = RoyalBlue,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = RoyalBlue
        )
    }
}

@Composable
fun PartnerCard(
    name: String,
    description: String,
    rating: Double,
    reviewsCount: String,
    benefits: List<String>,
    ctaText: String = "Continue to Provider",
    badge: String? = null,
    onContinueClick: () -> Unit
) {
    var isOpening by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (ChromeTabManager.openingTabState.value == null) {
                    isOpening = false
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        ChromeTabManager.openingTabState.collect { state ->
            if (state == null) {
                isOpening = false
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (badge != null) {
                        Surface(
                            color = RoyalBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = badge,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = AccentGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "$rating/5",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Text(
                        text = " ($reviewsCount)",
                        fontSize = 11.sp,
                        color = TextSecondaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = TextSecondaryLight,
                lineHeight = 16.sp
            )

            if (benefits.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                benefits.forEach { benefit ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = benefit,
                            fontSize = 12.sp,
                            color = TextPrimaryLight
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = {
                    if (!isOpening) {
                        isOpening = true
                        onContinueClick()
                        scope.launch {
                            kotlinx.coroutines.delay(10000)
                            isOpening = false
                        }
                    }
                },
                enabled = !isOpening,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RoyalBlue,
                    contentColor = SurfaceWhite,
                    disabledContainerColor = RoyalBlue,
                    disabledContentColor = SurfaceWhite
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (isOpening) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = SurfaceWhite,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Opening Marketplace...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                } else {
                    Text(
                        text = ctaText,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(
                text = "🔒 Opens securely in Chrome Custom Tab",
                fontSize = 10.sp,
                color = TextSecondaryLight,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(10.dp)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(it, color = TextSecondaryLight) } },
        placeholder = placeholder?.let { { Text(it, color = TextSecondaryLight) } },
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = NavyDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = NavyDark,
            unfocusedTextColor = NavyDark,
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            focusedBorderColor = RoyalBlue,
            unfocusedBorderColor = Color(0xFFCBD5E1),
            focusedLabelColor = RoyalBlue,
            unfocusedLabelColor = TextSecondaryLight,
            focusedPlaceholderColor = TextSecondaryLight,
            unfocusedPlaceholderColor = TextSecondaryLight
        )
    )
}

@Composable
fun CarQixPlayStoreLogo(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 180.dp
) {
    Surface(
        modifier = modifier
            .size(size)
            .aspectRatio(1f),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFF1F5F9)
                        )
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Car Emblem Container using App Icon Foreground Vector
                Surface(
                    color = RoyalBlue,
                    shape = CircleShape,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(size * 0.48f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = com.aaapp.appguru.myusedcarsaleuk.R.drawable.ic_launcher_foreground),
                            contentDescription = "CarQix App Icon",
                            modifier = Modifier.size(size * 0.44f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Brand Name "CarQix UK" Below Image
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "CarQix",
                        fontSize = (size.value * 0.11f).sp,
                        fontWeight = FontWeight.Black,
                        color = NavyDark,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        color = ErrorRed,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "UK",
                            fontSize = (size.value * 0.08f).sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MarketplaceLoadingOverlay(
    tabState: com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager.OpeningTabState?,
    onDismiss: () -> Unit
) {
    if (tabState != null) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.52f))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    modifier = Modifier
                        .widthIn(max = 320.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(60.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(52.dp),
                                color = RoyalBlue,
                                strokeWidth = 3.5.dp
                            )
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null,
                                tint = RoyalBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Opening Marketplace",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = NavyDark,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = tabState.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondaryLight,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            color = SlateBackground,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Secure",
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Connecting to official portal...",
                                    fontSize = 11.5.sp,
                                    color = TextSecondaryLight
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



