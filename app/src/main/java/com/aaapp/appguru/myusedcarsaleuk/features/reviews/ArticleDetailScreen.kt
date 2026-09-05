package com.aaapp.appguru.myusedcarsaleuk.features.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun ArticleDetailScreen(
    articleId: String,
    onNavigateRoute: (String) -> Unit
) {
    val article = AutomotiveDataProvider.articles.firstOrNull { it.id == articleId }
        ?: AutomotiveDataProvider.articles.first()

    Scaffold(
        topBar = {
            AppTopBar(
                title = article.category,
                subtitle = "${article.readTimeMinutes} min read",
                showBackButton = true,
                onBackClick = { onNavigateRoute("reviews_guides") },
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

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Surface(
                            color = RoyalBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = article.tag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = article.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Published ${article.datePublished} • ${article.readTimeMinutes} min read",
                            fontSize = 11.sp,
                            color = TextSecondaryLight
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(alpha = 0.5f))
                        Text(
                            text = article.fullContent,
                            fontSize = 14.sp,
                            color = TextPrimaryLight,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isArticleDetailNativeAdEnabled,
                    showMedia = true
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
