package com.example.newsapp.feature_news.domain.use_case

import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.repository.NewsRepository
import javax.inject.Inject


class SaveArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.upsertArticle(article = article)
    }
}