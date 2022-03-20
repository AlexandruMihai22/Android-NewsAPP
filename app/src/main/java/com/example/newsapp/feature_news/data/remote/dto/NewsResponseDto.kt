package com.example.newsapp.feature_news.data.remote.dto

import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.model.NewsResponse

data class NewsResponseDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) {
    fun toNewsResponse(): NewsResponse {
        return NewsResponse(
            articles = articles,
            status = status,
            totalResults = totalResults
        )
    }
}