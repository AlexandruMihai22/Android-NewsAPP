package com.example.newsapp.feature_news.presentation.savedNews

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.feature_news.presentation.components.ArticleListItem
import com.example.newsapp.feature_news.presentation.components.ArticlesEvent
import com.example.newsapp.feature_news.presentation.components.CustomTab

@ExperimentalMaterialApi
@Composable
fun SavedNewsScreen (
    viewModel: SavedNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "saved_article_list" }
        ) {
            items(items = state.articles, key = { article -> article.url!! }) { article ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        when(it) {
                            DismissValue.DismissedToStart -> {
                                viewModel.onEvent(ArticlesEvent.DeleteArticle(article = article))
                            }
                            DismissValue.DismissedToEnd -> {

                            }
                            DismissValue.Default -> {

                            }
                        }
                        true
                    }
                )
            
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }
                        )
                        val icon  = when(direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }

                        val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                        val alignment = when(direction) {
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(start = 12.dp, end = 12.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(icon, contentDescription = "Icon", modifier = Modifier.scale(scale))
                        }

                    },
                    dismissContent = {
                        Card(modifier = Modifier.fillMaxWidth(),
                            elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
                        ) {
                            ArticleListItem(
                                article = article,
                                onItemClick = {
                                    CustomTab.launch(context, article.url.toString())
                                }
                            )
                        }
                    }
                )
            }
        }
        if(state.error.isNotBlank()) {
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
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}


