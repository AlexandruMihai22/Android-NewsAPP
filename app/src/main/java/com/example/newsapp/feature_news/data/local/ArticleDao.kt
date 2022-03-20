package com.example.newsapp.feature_news.data.local

import androidx.room.*
import com.example.newsapp.feature_news.data.local.entity.ArticleEntity
import com.example.newsapp.feature_news.domain.model.Source


@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticleEntity")
    suspend fun getAllArticles(): List<ArticleEntity>

    suspend fun upsertArticle(article:ArticleEntity) {
        if(article.url?.let { getDuplicates(url = it, source = article.source).isEmpty() } == true) {
            upsertUtil(article = article)
        }
    }

    suspend fun deleteArticle(article: ArticleEntity) {
        article.url?.let { deleteUtil(it, article.source) }
    }

    @Query("DELETE FROM articleEntity WHERE url = :url AND source = :source")
    suspend fun deleteUtil(url:String, source: Source?)

    @Query("SELECT * FROM ArticleEntity WHERE url = :url AND source = :source")
    suspend fun getDuplicates(url: String, source: Source?): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUtil(article: ArticleEntity): Long

}
