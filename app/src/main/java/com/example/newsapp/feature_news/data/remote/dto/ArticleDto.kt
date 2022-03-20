package com.example.newsapp.feature_news.data.remote.dto


import com.example.newsapp.feature_news.data.local.entity.ArticleEntity
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.model.Source

data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }

    fun toArticle(): Article {
        return Article(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}