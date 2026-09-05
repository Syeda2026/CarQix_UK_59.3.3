package com.aaapp.appguru.myusedcarsaleuk.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun GlobalSearchScreen(
    viewModel: SearchViewModel,
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
    var redirectingKey by remember { mutableStateOf<String?>(null) }

    val marketplaces = AutomotiveDataProvider.marketplaces

    val articles = AutomotiveDataProvider.articles
    val providers = AutomotiveDataProvider.sellingProviders + AutomotiveDataProvider.financeProviders + AutomotiveDataProvider.insuranceProviders

    val filteredMarketplaces = if (query.isBlank()) emptyList() else marketplaces.filter {
        it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    val filteredArticles = if (query.isBlank()) emptyList() else articles.filter {
        it.title.contains(query, ignoreCase = true) || it.summary.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
    }

    val filteredProviders = if (query.isBlank()) emptyList() else providers.filter {
        it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Global Search",
                subtitle = "Marketplaces, Services & Guides",
                showBackButton = true,
                onBackClick = { onNavigateRoute("home") },
                onMoreClick = { onNavigateRoute("more") }
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "global_search",
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

            // Search Bar Input
            item {
                AppTextField(
                    value = query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    placeholder = "Search BMW, AutoTrader, Insurance, Tax...",
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = RoyalBlue) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onQueryChanged("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondaryLight)
                            }
                        }
                    }
                )
            }

            if (query.isBlank()) {
                // Popular Search Suggestions
                item {
                    Text(
                        text = "POPULAR SEARCHES",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = NavyDark
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val popular = listOf("Buy Used Cars", "Road Tax Calculator", "Value My Car", "Car Insurance", "Vehicle History Check", "AA Breakdown")
                        popular.forEach { pop ->
                            BlinkCard(
                                onClick = {
                                    viewModel.onQueryChanged(pop)
                                    viewModel.executeSearch(pop)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = pop, fontSize = 13.sp, color = NavyDark, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }

                if (recentSearches.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "RECENT SEARCH HISTORY", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark)
                            BlinkTextButton(onClick = { viewModel.clearRecentSearches() }) {
                                Text("Clear", fontSize = 11.sp, color = ErrorRed)
                            }
                        }
                    }

                    items(recentSearches) { recent ->
                        BlinkCard(
                            onClick = { viewModel.onQueryChanged(recent.query) },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.History, contentDescription = null, tint = TextSecondaryLight, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = recent.query, fontSize = 13.sp, color = NavyDark)
                            }
                        }
                    }
                }
            } else {
                // Search Results Sections
                if (filteredMarketplaces.isNotEmpty()) {
                    item { Text(text = "MARKETPLACES (${filteredMarketplaces.size})", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }
                    items(filteredMarketplaces) { marketplace ->
                        val isCurrentRedirecting = redirectingKey == marketplace.webUrlKey
                        Card(
                            onClick = {
                                if (redirectingKey == null) {
                                    viewModel.executeSearch(query)
                                    val url = ConfigManager.getResolvedUrl(marketplace.webUrlKey)
                                    ChromeTabManager.launchUrl(
                                        context = context,
                                        url = url,
                                        title = "${marketplace.name} - Used Cars UK",
                                        imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                    ) { loading -> 
                                        redirectingKey = if (loading) marketplace.webUrlKey else null
                                    }
                                }
                            },
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = marketplace.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                                    Text(text = marketplace.description, fontSize = 11.sp, color = TextSecondaryLight, maxLines = 1)
                                }
                                if (isCurrentRedirecting) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = RoyalBlue, strokeWidth = 2.5.dp)
                                } else {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RoyalBlue)
                                }
                            }
                        }
                    }
                }

                if (filteredProviders.isNotEmpty()) {
                    item { Text(text = "SERVICES & PROVIDERS (${filteredProviders.size})", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }
                    items(filteredProviders) { provider ->
                        val isCurrentRedirecting = redirectingKey == provider.partnerKey
                        Card(
                            onClick = {
                                if (redirectingKey == null) {
                                    viewModel.executeSearch(query)
                                    val url = ConfigManager.getResolvedUrl(provider.partnerKey)
                                    ChromeTabManager.launchUrl(
                                        context = context,
                                        url = url,
                                        title = "${provider.name} - UK Automotive",
                                        imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400"
                                    ) { loading -> 
                                        redirectingKey = if (loading) provider.partnerKey else null
                                    }
                                }
                            },
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {

                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = provider.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                                    Text(text = provider.description, fontSize = 11.sp, color = TextSecondaryLight, maxLines = 1)
                                }
                                if (isCurrentRedirecting) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = RoyalBlue, strokeWidth = 2.5.dp)
                                } else {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RoyalBlue)
                                }
                            }
                        }
                    }
                }

                if (filteredArticles.isNotEmpty()) {
                    item { Text(text = "GUIDES & ARTICLES (${filteredArticles.size})", fontSize = 12.sp, fontWeight = FontWeight.Black, color = NavyDark) }
                    items(filteredArticles) { article ->
                        BlinkCard(
                            onClick = {
                                viewModel.executeSearch(query)
                                onNavigateRoute("article_detail/${article.id}")
                            },
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = article.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                                    Text(text = article.summary, fontSize = 11.sp, color = TextSecondaryLight, maxLines = 1)
                                }
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RoyalBlue)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
