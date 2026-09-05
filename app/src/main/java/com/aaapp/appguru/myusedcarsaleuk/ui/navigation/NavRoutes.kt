package com.aaapp.appguru.myusedcarsaleuk.ui.navigation

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Onboarding : NavRoutes("onboarding")
    object Home : NavRoutes("home")
    object BuyCars : NavRoutes("buy_cars")
    object SellCar : NavRoutes("sell_car")
    object ValueCar : NavRoutes("value_car")
    object CarFinance : NavRoutes("car_finance")
    object CarInsurance : NavRoutes("car_insurance")
    object VehicleHistory : NavRoutes("vehicle_history")
    object BreakdownCover : NavRoutes("breakdown_cover")
    object ReviewsGuides : NavRoutes("reviews_guides")
    object ArticleDetail : NavRoutes("article_detail/{articleId}") {
        fun createRoute(articleId: String) = "article_detail/$articleId"
    }
    object BuyingAdvice : NavRoutes("buying_advice")
    object CoreServices : NavRoutes("core_services")
    object SmartTools : NavRoutes("smart_tools")
    object PartnerServices : NavRoutes("partner_services")
    object CalculatorDetail : NavRoutes("calculator_detail/{calculatorId}") {
        fun createRoute(calculatorId: String) = "calculator_detail/$calculatorId"
    }
    object GlobalSearch : NavRoutes("global_search")
    object Saved : NavRoutes("saved")
    object More : NavRoutes("more")
    object HowToUse : NavRoutes("how_to_use")
    object Settings : NavRoutes("settings")
    object About : NavRoutes("about")
    object PrivacyPolicy : NavRoutes("privacy_policy")
    object Terms : NavRoutes("terms")
}
