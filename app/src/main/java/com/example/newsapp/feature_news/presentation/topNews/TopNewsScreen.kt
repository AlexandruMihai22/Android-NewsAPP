package com.example.newsapp.feature_news.presentation.topNews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.newsapp.feature_news.presentation.components.ArticleListItem
import com.example.newsapp.feature_news.presentation.components.ArticlesEvent
import com.example.newsapp.feature_news.presentation.components.CustomTab
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TopNewsScreen (
    viewModel: NewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val page = viewModel.page.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp)

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "article_list" }
        ) {
            itemsIndexed(
                items = state.articles
            ) { index, article ->
                val saveArticle = SwipeAction(
                    icon = {
                           Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "add_button")
                    },
                    background = Green,
                    onSwipe = { viewModel.onEvent(ArticlesEvent.SaveArticle(article = article)) }
                )
                viewModel.onChangeArticleScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !state.isLoading) {
                    viewModel.nextPage()
                }
                SwipeableActionsBox(
                    swipeThreshold = 60.dp,
                    modifier = Modifier.background(White),
                    startActions = listOf(saveArticle)
                    ) {
                    Card(modifier = Modifier.fillMaxWidth()
                    ) {
                        ArticleListItem(
                            article = article,
                            onItemClick = {
                                CustomTab.launch(context, article.url.toString())
                            }
                        )
                    }
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}