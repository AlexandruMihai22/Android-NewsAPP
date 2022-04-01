package com.example.newsapp.feature_news.presentation.bottomNav

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.feature_news.presentation.savedNews.SavedNewsScreen
import com.example.newsapp.feature_news.presentation.searchNews.SearchNewsScreen
import com.example.newsapp.feature_news.presentation.topNews.TopNewsScreen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.TopNews.route
    ) {
        composable(route = BottomNavItem.TopNews.route) {
            TopNewsScreen()
        }
        composable(route = BottomNavItem.SearchNews.route) {
            SearchNewsScreen()
        }
        composable(route = BottomNavItem.SavedNews.route) {
            SavedNewsScreen()
        }
    }
}