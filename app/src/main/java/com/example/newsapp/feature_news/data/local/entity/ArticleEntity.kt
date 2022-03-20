package com.example.newsapp.feature_news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.model.Source


@Entity
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    @PrimaryKey val id: Int? = null
) {
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
