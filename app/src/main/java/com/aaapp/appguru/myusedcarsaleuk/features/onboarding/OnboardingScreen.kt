package com.aaapp.appguru.myusedcarsaleuk.features.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.R
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    dataStoreManager: DataStoreManager,
    onOnboardingFinished: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pages = listOf(
        OnboardingPageData(
            title = "Find Your Perfect Car",
            description = "Compare thousands of used cars across top UK marketplaces like AutoTrader, Motors, and CarGurus in one app.",
            icon = Icons.Default.DirectionsCar,
            color = RoyalBlue
        ),
        OnboardingPageData(
            title = "All Automotive Tools & Services",
            description = "Value your car, calculate road tax, compare finance rates, insurance, and vehicle history checks effortlessly.",
            icon = Icons.Default.Calculate,
            color = AccentGoldDark
        ),
        OnboardingPageData(
            title = "Privacy First & 100% Free",
            description = "No accounts required, no spam, no stored personal details. Discover trusted UK services and redirect securely.",
            icon = Icons.Default.Security,
            color = SuccessGreen
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    fun finishOnboarding() {
        scope.launch {
            dataStoreManager.setOnboardingCompleted(true)
            onOnboardingFinished()
        }
    }

    // Intercept back button: go to previous slide if not on first slide
    BackHandler(enabled = true) {
        if (pagerState.currentPage > 0) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        } else {
            finishOnboarding()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
            .testTag("onboarding_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CarQix",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = NavyDark
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "UK",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = ErrorRed
                )
            }
            TextButton(
                onClick = { finishOnboarding() },
                modifier = Modifier.testTag("onboarding_skip_button")
            ) {
                Text(text = "Skip", color = TextSecondaryLight, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(0.15f))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .testTag("onboarding_pager")
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (pageIndex == 0) {
                    Surface(
                        color = RoyalBlue,
                        shape = CircleShape,
                        modifier = Modifier.size(150.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.app_logo),
                                contentDescription = "CarQix Car Icon",
                                modifier = Modifier.size(110.dp)
                            )
                        }
                    }
                } else {
                    Surface(
                        color = page.color.copy(alpha = 0.12f),
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = page.icon,
                                contentDescription = null,
                                tint = page.color,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = page.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = page.description,
                    fontSize = 14.sp,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { iteration ->
                val isCurrent = pagerState.currentPage == iteration
                val color = if (isCurrent) RoyalBlue else Color.LightGray
                val width = if (isCurrent) 24.dp else 8.dp
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(iteration)
                            }
                        }
                        .testTag("onboarding_dot_$iteration")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (pagerState.currentPage < pages.size - 1) {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    finishOnboarding()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("onboarding_action_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
        ) {
            Text(
                text = if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

private data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)
