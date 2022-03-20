package com.example.newsapp.feature_news.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.feature_news.data.local.ArticleDatabase
import com.example.newsapp.feature_news.data.remote.NewsApi
import com.example.newsapp.feature_news.data.repository.NewsRepositoryImpl
import com.example.newsapp.feature_news.domain.repository.NewsRepository
import com.example.newsapp.feature_news.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: NewsRepository): GetArticlesUseCase {
        return GetArticlesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSearchArticlesUseCase(repository: NewsRepository): GetSearchArticlesUseCase {
        return GetSearchArticlesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedArticlesUseCase(repository: NewsRepository): GetSavedArticlesUseCase {
        return GetSavedArticlesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteArticleUseCase(repository: NewsRepository): DeleteArticleUseCase {
        return DeleteArticleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveArticleUseCase(repository: NewsRepository): SaveArticleUseCase {
        return SaveArticleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        api: NewsApi,
        db: ArticleDatabase
    ): NewsRepository {
        return NewsRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideArticleDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(
            app, ArticleDatabase::class.java, "article_db"
        ).build()
    }
}