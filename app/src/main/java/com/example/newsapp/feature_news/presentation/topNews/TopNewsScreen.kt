package com.example.newsapp.feature_news.presentation.topNews

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.newsapp.feature_news.presentation.components.ArticleListItem
import com.example.newsapp.feature_news.presentation.components.CustomTab

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