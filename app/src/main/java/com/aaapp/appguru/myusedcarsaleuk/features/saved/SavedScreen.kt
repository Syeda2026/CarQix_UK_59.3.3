package com.aaapp.appguru.myusedcarsaleuk.features.saved

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun SavedScreen(
    viewModel: SavedViewModel,
    onNavigateRoute: (String) -> Unit
) {
    val savedItems by viewModel.savedItems.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var redirectingItemId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Saved Items",
                subtitle = "${savedItems.size} Saved Favorites & Calculations",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "saved",
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

            if (savedItems.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(color = RoyalBlue.copy(alpha = 0.1f), shape = CircleShape, modifier = Modifier.size(60.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.StarBorder, contentDescription = null, tint = NavyDark, modifier = Modifier.size(32.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "No Saved Favorites Yet", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Tap the Favorites Star icon (☆) on any custom tab or car listing to save deep links directly to your app favorites.",
                                fontSize = 12.sp,
                                color = TextSecondaryLight,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { onNavigateRoute("buy_cars") }, colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                                Text("Browse Cars")
                            }
                        }
                    }
                }
            } else {
                items(savedItems) { item ->
                    val isCarDeepLink = item.itemType == "CAR_DEEP_LINK" || item.subtitle.startsWith("http") || item.detailDataJson.startsWith("http")
                    val isCurrentItemRedirecting = (redirectingItemId == item.id)

                    BlinkCard(
                        onClick = {
                            if (isCarDeepLink && redirectingItemId == null) {
                                val targetUrl = if (item.subtitle.startsWith("http")) item.subtitle else item.detailDataJson
                                ChromeTabManager.launchUrl(
                                    context = context,
                                    url = targetUrl,
                                    title = item.title,
                                    imageUrl = item.imageUrl
                                ) { loading -> 
                                    redirectingItemId = if (loading) item.id else null
                                }
                            } else if (!isCarDeepLink) {
                                val route = item.detailDataJson
                                if (route.startsWith("calculator_detail/") || route.startsWith("article_detail/") || route == "buy_cars" || route == "smart_tools" || route == "reviews_guides") {
                                    onNavigateRoute(route)
                                } else {
                                    when (item.itemType) {
                                        "CALCULATION" -> onNavigateRoute("smart_tools")
                                        "SEARCH" -> onNavigateRoute("buy_cars")
                                        "ARTICLE" -> onNavigateRoute("reviews_guides")
                                        else -> onNavigateRoute("buy_cars")
                                    }
                                }
                            }
                        },
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isCarDeepLink && isCurrentItemRedirecting) {
                                Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(modifier = Modifier.size(28.dp), color = RoyalBlue, strokeWidth = 3.dp)
                                }
                            } else if (isCarDeepLink && item.imageUrl.isNotBlank()) {
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFE2E8F0))
                                )
                            } else {
                                Surface(
                                    color = when {
                                        isCarDeepLink -> Color(0xFFFEF08A) // Soft gold background for star favorites
                                        item.itemType == "CALCULATION" -> AccentGold.copy(0.15f)
                                        else -> RoyalBlue.copy(0.1f)
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (isCarDeepLink && isCurrentItemRedirecting) {
                                             CircularProgressIndicator(modifier = Modifier.size(24.dp), color = RoyalBlue, strokeWidth = 2.5.dp)
                                        } else {
                                            Icon(
                                                imageVector = when {
                                                    isCarDeepLink -> Icons.Default.Star
                                                    item.itemType == "CALCULATION" -> Icons.Default.Calculate
                                                    else -> Icons.Default.Bookmark
                                                },
                                                contentDescription = null,
                                                tint = when {
                                                    isCarDeepLink -> Color(0xFF1E293B) // Filled black/slate star
                                                    item.itemType == "CALCULATION" -> AccentGoldDark
                                                    else -> RoyalBlue
                                                },
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                if (isCarDeepLink) {
                                    val parsedHost = try { Uri.parse(item.subtitle).host?.removePrefix("www.") ?: "UK Dealer" } catch (e: Exception) { "UK Dealer" }
                                    Surface(
                                        color = Color(0xFFF1F5F9),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Text(
                                            text = "⭐ $parsedHost".uppercase(),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = NavyDark,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = if (isCarDeepLink) {
                                        "Tap to open listing"
                                    } else {
                                        item.subtitle
                                    },
                                    fontSize = 12.sp,
                                    color = RoyalBlue,
                                    fontWeight = if (isCarDeepLink) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            BlinkIconButton(onClick = { viewModel.deleteSavedItem(item.id) }) {
                                Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Delete", tint = ErrorRed)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

