package com.example.newsapp.feature_news.presentation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object TopNews : BottomNavItem(
        route = "top_news",
        title = "Top News",
        icon = Icons.Default.Home
    )

    object SearchNews : BottomNavItem(
        route = "search_news",
        title = "Search News",
        icon = Icons.Default.Search
    )

    object SavedNews : BottomNavItem(
        route = "saved_news",
        title = "Saved",
        icon = Icons.Default.Star
    )
}
