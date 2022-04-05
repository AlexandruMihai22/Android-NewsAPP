package com.example.newsapp.feature_news.presentation.searchNews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.common.Resource
import com.example.newsapp.feature_news.domain.model.Article
import com.example.newsapp.feature_news.domain.use_case.DeleteArticleUseCase
import com.example.newsapp.feature_news.domain.use_case.GetSearchArticlesUseCase
import com.example.newsapp.feature_news.domain.use_case.SaveArticleUseCase
import com.example.newsapp.feature_news.presentation.components.ArticleListState
import com.example.newsapp.feature_news.presentation.components.ArticlesEvent
import com.example.newsapp.feature_news.presentation.searchWidget.SearchWidgetState
import com.example.newsapp.feature_news.presentation.topNews.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val getSearchArticlesUseCase: GetSearchArticlesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModel() {

    // Article list
    private val _state = mutableStateOf(ArticleListState())
    val state: State<ArticleListState> = _state
    private var currQuery = "a"
    val page = mutableStateOf(1)
    private var articleListScrollPosition = 0

    // Search Widget
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState
    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState


    init {
        getArticles(query = currQuery)
    }

     private fun getArticles(query: String) {
         resetState()
         getSearchArticlesUseCase(query, 1).onEach { result ->
             when(result) {
                is Resource.Success -> {
                    _state.value = ArticleListState(articles = result.data ?: emptyList())
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


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun onEvent(event: ArticlesEvent) {
        when (event) {
            is ArticlesEvent.SearchArticles -> {
                viewModelScope.launch {
                    getArticles(event.query)
                    currQuery = event.query
                }
            }
            is ArticlesEvent.SaveArticle -> {
                viewModelScope.launch {
                    saveArticleUseCase(event.article)
                }
            }
            is ArticlesEvent.DeleteArticle -> {
                viewModelScope.launch {
                    deleteArticleUseCase(event.article)
                }
            }
            else -> {}
        }
    }


    fun nextPage() {
        viewModelScope.launch {
            if ((articleListScrollPosition + 1) >= (page.value * PAGE_SIZE) && page.value < 5) {
                incrementPageNumber()
                delay(200)
                if (page.value > 1) {
                    getSearchArticlesUseCase(currQuery, pageNumber = page.value).onEach { result ->
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
        page.value += 1
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
