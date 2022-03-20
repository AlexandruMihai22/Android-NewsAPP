package com.example.newsapp.feature_news.presentation.notUsed

sealed class Screen(val route: String) {
    object WebViewScreen: Screen("web_view")
    object TopNewsScreen: Screen("top_news_screen")
    object SearchNewsScreen: Screen("search_news_screen")
}
