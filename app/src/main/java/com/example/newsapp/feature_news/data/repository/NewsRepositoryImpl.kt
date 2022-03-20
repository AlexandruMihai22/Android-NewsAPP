package com.example.newsapp.feature_news.data.repository

import com.example.newsapp.feature_news.data.local.ArticleDao
import com.example.newsapp.feature_news.data.remote.NewsApi
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.model.NewsResponse
import com.example.newsapp.feature_news.domain.repository.NewsRepository
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: ArticleDao
): NewsRepository {

    override suspend fun getTopNews(countryCode: String, pageNumber: Int): NewsResponse {
        return api.getTopNews(countryCode = countryCode, pageNumber = pageNumber).toNewsResponse()
    }

    override suspend fun searchForNews(searchQuery: String, pageNumber: Int): NewsResponse {
        return api.searchForNews(searchQuery = searchQuery, pageNumber = pageNumber).toNewsResponse()
    }

    override suspend fun upsertArticle(article: Article) {
        dao.upsertArticle(article = article.toArticleEntity())
    }

    override suspend fun getSavedArticles(): List<Article> {
        return dao.getAllArticles().map {it.toArticle()}
    }

    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article = article.toArticleEntity())
    }
}

