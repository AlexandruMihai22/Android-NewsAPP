package com.example.newsapp.feature_news.presentation.searchNews

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.feature_news.presentation.components.ArticleListItem
import com.example.newsapp.feature_news.presentation.components.ArticlesEvent
import com.example.newsapp.feature_news.presentation.components.CustomTab
import com.example.newsapp.feature_news.presentation.searchWidget.SearchWidget
import com.example.newsapp.feature_news.presentation.searchWidget.SearchWidgetState
import com.example.newsapp.feature_news.presentation.topNews.PAGE_SIZE


@Composable
fun SearchNewsScreen (
    viewModel: SearchNewsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.state.value
    val page = viewModel.page.value

    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    Scaffold(
        topBar = {
            SearchWidget(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text: ", it)
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    viewModel.onEvent(ArticlesEvent.SearchArticles(it))
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 48.dp)

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(
                items = state.articles
            ) { index, article  ->
                viewModel.onChangeArticleScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !state.isLoading) {
                    viewModel.nextPage()
                }
                ArticleListItem(
                    article = article,
                    onItemClick = {
                        CustomTab.launch(context, article.url.toString())
                        viewModel.onEvent(ArticlesEvent.SaveArticle(it)) // For Testing
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


