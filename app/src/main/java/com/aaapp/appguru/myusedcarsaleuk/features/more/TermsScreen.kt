package com.aaapp.appguru.myusedcarsaleuk.features.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun TermsScreen(
    onNavigateRoute: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Terms of Service",
                subtitle = "App Usage Conditions",
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

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "Terms of Service", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = """
                                By using the CarQix UK application, you agree to the following terms:
                                
                                1. Information Purpose: Information and calculations provided in this app (such as estimated road tax, loan repayment estimates, and insurance group estimates) are for guidance only.
                                
                                2. Partner Listings: Vehicle listings, vehicle values, and finance quotes are provided directly by external official partner platforms. Final contract terms are agreed on partner websites.
                                
                                3. Intellectual Property: All trademarks (e.g. AutoTrader, Motorway, AA, RAC) belong to their respective owners and are used for comparative reference.
                            """.trimIndent(),
                            fontSize = 12.sp,
                            color = TextPrimaryLight,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
