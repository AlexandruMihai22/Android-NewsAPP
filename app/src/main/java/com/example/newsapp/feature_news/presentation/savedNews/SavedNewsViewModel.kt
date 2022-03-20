package com.example.newsapp.feature_news.presentation.savedNews

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.common.Resource
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.use_case.DeleteArticleUseCase
import com.example.newsapp.feature_news.domain.use_case.GetSavedArticlesUseCase
import com.example.newsapp.feature_news.domain.use_case.SaveArticleUseCase
import com.example.newsapp.feature_news.presentation.components.ArticleListState
import com.example.newsapp.feature_news.presentation.components.ArticlesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val saveArticleUseCase: SaveArticleUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ArticleListState())
    val state: State<ArticleListState> = _state

    private var recentlyDeletedArticle: Article? = null


    init {
        getArticles()
    }

    private fun getArticles() {
        getSavedArticlesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ArticleListState(articles = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        ArticleListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = ArticleListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ArticlesEvent) {
        when (event) {
            is ArticlesEvent.SaveArticle -> {
                viewModelScope.launch {
                    saveArticleUseCase(event.article)
                }
            }
            is ArticlesEvent.DeleteArticle -> {
                viewModelScope.launch {
                    val current = ArrayList(_state.value.articles)
                    current.remove(event.article)
                    _state.value= ArticleListState(articles = current)
                    deleteArticleUseCase(event.article)
                    recentlyDeletedArticle = event.article
                }
            }
            is ArticlesEvent.RestoreArticle -> {
                viewModelScope.launch {
                    saveArticleUseCase(recentlyDeletedArticle ?: return@launch)
                    recentlyDeletedArticle = null
                }
            }
            else -> {}
        }
    }

}

