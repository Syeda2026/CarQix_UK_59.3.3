package com.aaapp.appguru.myusedcarsaleuk.ui.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aaapp.appguru.myusedcarsaleuk.common.CustomerRatingDialog
import com.aaapp.appguru.myusedcarsaleuk.common.MarketplaceLoadingOverlay
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.aaapp.appguru.myusedcarsaleuk.features.advice.BuyingAdviceScreen
import com.aaapp.appguru.myusedcarsaleuk.features.buy.BuyCarsScreen
import com.aaapp.appguru.myusedcarsaleuk.features.buy.BuyCarsViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.breakdown.BreakdownCoverScreen
import com.aaapp.appguru.myusedcarsaleuk.features.finance.CarFinanceScreen
import com.aaapp.appguru.myusedcarsaleuk.features.history.VehicleHistoryScreen
import com.aaapp.appguru.myusedcarsaleuk.features.home.CoreServicesScreen
import com.aaapp.appguru.myusedcarsaleuk.features.home.HomeScreen
import com.aaapp.appguru.myusedcarsaleuk.features.home.HomeViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.insurance.CarInsuranceScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.AboutScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.HowToUseScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.MoreScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.PrivacyPolicyScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.SettingsScreen
import com.aaapp.appguru.myusedcarsaleuk.features.more.TermsScreen
import com.aaapp.appguru.myusedcarsaleuk.features.onboarding.OnboardingScreen
import com.aaapp.appguru.myusedcarsaleuk.features.partners.PartnerServicesScreen
import com.aaapp.appguru.myusedcarsaleuk.features.reviews.ArticleDetailScreen
import com.aaapp.appguru.myusedcarsaleuk.features.reviews.ReviewsGuidesScreen
import com.aaapp.appguru.myusedcarsaleuk.features.saved.SavedScreen
import com.aaapp.appguru.myusedcarsaleuk.features.saved.SavedViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.splash.UpdateViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.search.GlobalSearchScreen
import com.aaapp.appguru.myusedcarsaleuk.features.search.SearchViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.sell.SellCarScreen
import com.aaapp.appguru.myusedcarsaleuk.features.splash.SplashScreen
import com.aaapp.appguru.myusedcarsaleuk.features.tools.CalculatorDetailScreen
import com.aaapp.appguru.myusedcarsaleuk.features.tools.SmartToolsScreen
import com.aaapp.appguru.myusedcarsaleuk.features.value.ValueCarScreen

object NavigationSessionState {
    var hasShownRatingThisSession: Boolean = false
    var visitedScreensCount: Int = 0
    var lastVisitedRoute: String? = null
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    dataStoreManager: DataStoreManager,
    updateViewModel: UpdateViewModel,
    updateLauncher: ActivityResultLauncher<IntentSenderRequest>
) {
    val hasRatedApp by dataStoreManager.hasRatedApp.collectAsStateWithLifecycle(initialValue = false)
    val openingTabState by ChromeTabManager.openingTabState.collectAsStateWithLifecycle()
    var showRatingDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Trigger rating popup on the 3rd internal screen visited (not on external link clicks)
    LaunchedEffect(currentRoute, hasRatedApp) {
        if (currentRoute != null) {
            com.aaapp.appguru.myusedcarsaleuk.core.analytics.AnalyticsManager.logScreenView(currentRoute)
            com.aaapp.appguru.myusedcarsaleuk.core.analytics.CrashlyticsManager.setCustomKey("current_screen", currentRoute)
        }

        if (currentRoute != null &&
            currentRoute != NavRoutes.Splash.route &&
            currentRoute != NavRoutes.Onboarding.route
        ) {
            if (NavigationSessionState.lastVisitedRoute != currentRoute) {
                NavigationSessionState.lastVisitedRoute = currentRoute
                NavigationSessionState.visitedScreensCount++
            }

            if (NavigationSessionState.visitedScreensCount >= 3 &&
                !hasRatedApp &&
                !NavigationSessionState.hasShownRatingThisSession
            ) {
                NavigationSessionState.hasShownRatingThisSession = true
                delay(600)
                showRatingDialog = true
            }
        }
    }

    if (showRatingDialog && !hasRatedApp) {
        CustomerRatingDialog(
            onDismiss = {
                showRatingDialog = false
            },
            onRateNow = {
                showRatingDialog = false
                coroutineScope.launch {
                    dataStoreManager.setHasRatedApp(true)
                }
            }
        )
    }

    MarketplaceLoadingOverlay(
        tabState = openingTabState,
        onDismiss = {
            ChromeTabManager.dismissLoading()
        }
    )

    val context = LocalContext.current
    val handleGlobalNavigation: (String) -> Unit = { route ->
        val isToolOrGuide = route.startsWith("calculator_detail/") ||
                route == NavRoutes.BuyingAdvice.route ||
                route.startsWith("article_detail/") ||
                route == NavRoutes.SmartTools.route ||
                route == NavRoutes.ReviewsGuides.route

        if (isToolOrGuide) {
            AdManager.showToolsInterstitialAd(context) {
                navController.navigate(route)
            }
        } else {
            navController.navigate(route)
        }
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Splash.route,
            enterTransition = {
                fadeIn(animationSpec = tween(220, easing = LinearOutSlowInEasing)) +
                        scaleIn(initialScale = 0.96f, animationSpec = tween(220, easing = LinearOutSlowInEasing))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(180, easing = FastOutLinearInEasing)) +
                        scaleOut(targetScale = 0.98f, animationSpec = tween(180, easing = FastOutLinearInEasing))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(220, easing = LinearOutSlowInEasing)) +
                        scaleIn(initialScale = 0.98f, animationSpec = tween(220, easing = LinearOutSlowInEasing))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(180, easing = FastOutLinearInEasing)) +
                        scaleOut(targetScale = 0.96f, animationSpec = tween(180, easing = FastOutLinearInEasing))
            }
        ) {
        // Splash Screen
        composable(NavRoutes.Splash.route) {
            SplashScreen(
                dataStoreManager = dataStoreManager,
                updateViewModel = updateViewModel,
                updateLauncher = updateLauncher,
                onNavigateOnboarding = {
                    navController.navigate(NavRoutes.Onboarding.route) {
                        popUpTo(NavRoutes.Splash.route) { inclusive = true }
                    }
                },
                onNavigateHome = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding Screen
        composable(NavRoutes.Onboarding.route) {
            OnboardingScreen(
                dataStoreManager = dataStoreManager,
                onOnboardingFinished = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Home Dashboard
        composable(NavRoutes.Home.route) {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Buy Used Cars
        composable(NavRoutes.BuyCars.route) {
            val buyCarsViewModel: BuyCarsViewModel = viewModel()
            BuyCarsScreen(
                viewModel = buyCarsViewModel,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Sell Your Car
        composable(NavRoutes.SellCar.route) {
            SellCarScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Value My Car
        composable(NavRoutes.ValueCar.route) {
            ValueCarScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Car Finance
        composable(NavRoutes.CarFinance.route) {
            CarFinanceScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Car Insurance
        composable(NavRoutes.CarInsurance.route) {
            CarInsuranceScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Vehicle History Check
        composable(NavRoutes.VehicleHistory.route) {
            VehicleHistoryScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Breakdown Cover
        composable(NavRoutes.BreakdownCover.route) {
            BreakdownCoverScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Reviews & Guides
        composable(NavRoutes.ReviewsGuides.route) {
            ReviewsGuidesScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Article Detail
        composable(
            route = NavRoutes.ArticleDetail.route,
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: "guide_1"
            ArticleDetailScreen(
                articleId = articleId,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Buying Advice & Tips
        composable(NavRoutes.BuyingAdvice.route) {
            BuyingAdviceScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Core Services
        composable(NavRoutes.CoreServices.route) {
            CoreServicesScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Smart Tools
        composable(NavRoutes.SmartTools.route) {
            SmartToolsScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Partner Services
        composable(NavRoutes.PartnerServices.route) {
            PartnerServicesScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Calculator Detail
        composable(
            route = NavRoutes.CalculatorDetail.route,
            arguments = listOf(navArgument("calculatorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val calcId = backStackEntry.arguments?.getString("calculatorId") ?: "road_tax"
            CalculatorDetailScreen(
                calculatorId = calcId,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Global Search
        composable(NavRoutes.GlobalSearch.route) {
            val searchViewModel: SearchViewModel = viewModel()
            GlobalSearchScreen(
                viewModel = searchViewModel,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // Saved Items
        composable(NavRoutes.Saved.route) {
            val savedViewModel: SavedViewModel = viewModel()
            SavedScreen(
                viewModel = savedViewModel,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        // More & Settings
        composable(NavRoutes.More.route) {
            MoreScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        composable(NavRoutes.Settings.route) {
            SettingsScreen(
                dataStoreManager = dataStoreManager,
                onNavigateRoute = handleGlobalNavigation
            )
        }

        composable(NavRoutes.About.route) {
            AboutScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        composable(NavRoutes.HowToUse.route) {
            HowToUseScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        composable(NavRoutes.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }

        composable(NavRoutes.Terms.route) {
            TermsScreen(
                onNavigateRoute = handleGlobalNavigation
            )
        }
    }
}
}
