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
fun PrivacyPolicyScreen(
    onNavigateRoute: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Privacy Policy",
                subtitle = "Your Data Protection Guarantee",
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
                        Text(text = "Privacy First Commitment", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = """
                                At CarQix UK, your privacy is our highest priority.
                                
                                1. No Account Required: You can use all tools, calculators, and search features without creating an account or providing personal identification.
                                
                                2. Local-Only Storage: All saved searches, filter preferences, and saved calculation results remain strictly stored on your own device using local encrypted Room database storage.
                                
                                3. External Redirection: When you choose to visit an official partner (e.g. AutoTrader, Motorway, Experian, AA), you are safely redirected via secure Chrome Custom Tabs. No financial or credit data is processed within this app.
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
