package com.example.newsapp.feature_news.presentation.components

import com.example.newsapp.feature_news.domain.model.Article

data class ArticleListState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String = ""
)
