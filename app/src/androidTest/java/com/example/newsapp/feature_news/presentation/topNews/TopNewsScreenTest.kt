package com.example.newsapp.feature_news.presentation.topNews

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MainActivity
import com.example.newsapp.feature_news.di.AppModule
import com.example.newsapp.feature_news.presentation.bottomNav.BottomNavItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TopNewsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.TopNews.route
            ) {
                composable(route = BottomNavItem.TopNews.route) {
                    TopNewsScreen()
                }
            }
        }
    }

    @Test
    fun check_article_list_appear_on_screen() {
        composeRule.onNodeWithContentDescription("article_list")
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(5)
    }
}