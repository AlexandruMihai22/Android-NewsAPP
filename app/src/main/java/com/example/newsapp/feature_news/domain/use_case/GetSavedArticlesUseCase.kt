package com.example.newsapp.feature_news.domain.use_case

import com.example.newsapp.common.Resource
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetSavedArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading<List<Article>>())
            val articles =  repository.getSavedArticles()
            emit(Resource.Success<List<Article>>(articles))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Article>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Article>>("Couldn't reach server. Check your internet connection."))
        }
    }
}