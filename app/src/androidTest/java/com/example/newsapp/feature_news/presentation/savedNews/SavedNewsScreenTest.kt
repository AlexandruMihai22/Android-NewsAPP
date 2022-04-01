package com.example.newsapp.feature_news.presentation.savedNews

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MainActivity
import com.example.newsapp.feature_news.di.AppModule
import com.example.newsapp.feature_news.presentation.bottomNav.BottomNavItem
import com.example.newsapp.feature_news.presentation.topNews.TopNewsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class SavedNewsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.SavedNews.route
            ) {
                composable(route = BottomNavItem.TopNews.route) {
                    TopNewsScreen()
                }
                composable(route = BottomNavItem.SavedNews.route) {
                    SavedNewsScreen()
                }
            }
        }
    }

    @Test
    fun check_article_list_appear_on_screen() {
        Thread.sleep(500)
        composeRule.onNodeWithContentDescription("saved_article_list")
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(0)
    }
}