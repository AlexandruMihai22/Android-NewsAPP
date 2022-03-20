package com.example.newsapp.feature_news.domain.repository

import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.model.NewsResponse


interface NewsRepository {
    suspend fun getTopNews(countryCode: String, pageNumber: Int): NewsResponse
    suspend fun searchForNews(searchQuery: String, pageNumber: Int): NewsResponse
    suspend fun upsertArticle(article:Article)
    suspend fun getSavedArticles(): List<Article>
    suspend fun deleteArticle(article: Article)
}