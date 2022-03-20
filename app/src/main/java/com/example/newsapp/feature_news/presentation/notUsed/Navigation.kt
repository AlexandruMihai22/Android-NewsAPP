package com.example.newsapp.feature_news.presentation.notUsed

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.feature_news.presentation.topNews.TopNewsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.TopNewsScreen.route
    ) {
        composable(route = Screen.TopNewsScreen.route) {
            TopNewsScreen()
        }
        composable(route = Screen.WebViewScreen.route + "/{articleUrl}") { navBackStack ->
            val articleUrl = navBackStack.arguments?.getString("articleUrl")
            WebViewScreen(url = "www.google.com/$articleUrl")
        }
    }
}