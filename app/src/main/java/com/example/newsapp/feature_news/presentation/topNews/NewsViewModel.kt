package com.example.newsapp.feature_news.presentation.topNews

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.common.Resource
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.use_case.GetArticlesUseCase
import com.example.newsapp.feature_news.presentation.components.ArticleListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 20


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ArticleListState())
    val state: State<ArticleListState> = _state

    val page = mutableStateOf(1)
    private var articleListScrollPosition = 0

    init {
        getFirstArticles()
    }

    private fun getFirstArticles() {
        resetState()
        getArticlesUseCase("us", pageNumber = 1).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = ArticleListState(articles = result.data ?: emptyList(), isLoading = false)
                }
                is Resource.Error -> {
                    _state.value = ArticleListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = ArticleListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun nextPage() {
        viewModelScope.launch {
            if ((articleListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                incrementPageNumber()
                delay(200)
                if (page.value > 1) {
                    getArticlesUseCase("us", pageNumber = page.value).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let { appendArticles(it) }
                            }
                            is Resource.Error -> {
                                _state.value = ArticleListState(
                                    articles = _state.value.articles,
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = ArticleListState(
                                    articles = _state.value.articles,
                                    isLoading = true
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private fun incrementPageNumber() {
        if(page.value < 5) {
            page.value += 1
        }
    }

    fun onChangeArticleScrollPosition(position: Int) {
        articleListScrollPosition = position
    }

    private fun appendArticles(articles: List<Article>) {
        val current = ArrayList(_state.value.articles)
        current.addAll(articles)
        _state.value = ArticleListState(articles = current, isLoading = false)
    }

    private fun resetState() {
        onChangeArticleScrollPosition(0)
        page.value = 1
    }
}
