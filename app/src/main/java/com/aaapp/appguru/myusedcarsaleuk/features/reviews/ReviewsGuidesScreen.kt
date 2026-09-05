package com.aaapp.appguru.myusedcarsaleuk.features.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.model.GuideArticle
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun ReviewsGuidesScreen(
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val articles = AutomotiveDataProvider.articles
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Buying Guides", "Finance", "Insurance", "Reviews")

    val filteredArticles = if (selectedCategory == "All") articles else articles.filter { it.category == selectedCategory }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Reviews & Guides",
                subtitle = "Expert Advice & Market Insights",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
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

            // Category Filter Row
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = RoyalBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            itemsIndexed(filteredArticles) { index, article ->
                BlinkCard(
                    onClick = { onNavigateRoute("article_detail/${article.id}") },
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = RoyalBlue.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = article.category,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalBlue,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(text = "${article.readTimeMinutes} min read", fontSize = 11.sp, color = TextSecondaryLight)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = article.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = article.summary,
                            fontSize = 12.sp,
                            color = TextSecondaryLight,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = article.datePublished, fontSize = 11.sp, color = TextSecondaryLight)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Read Article", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RoyalBlue)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }

                if (index == 1) {
                    AdMobNativeAdCard(
                        isPlacementEnabled = ConfigManager.adsConfig.isReviewsGuidesNativeAdEnabled
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
