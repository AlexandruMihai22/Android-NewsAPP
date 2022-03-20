package com.example.newsapp.feature_news.presentation.components

import com.example.newsapp.feature_news.domain.model.Article

sealed class ArticlesEvent {
    data class SaveArticle(val article: Article): ArticlesEvent()
    data class DeleteArticle(val article: Article): ArticlesEvent()
    data class SearchArticles(val query: String): ArticlesEvent()
    object RestoreArticle: ArticlesEvent()
}
