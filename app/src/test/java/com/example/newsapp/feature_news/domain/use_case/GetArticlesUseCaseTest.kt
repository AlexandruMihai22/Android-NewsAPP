package com.example.newsapp.feature_news.domain.use_case
import com.example.newsapp.feature_news.domain.model.NewsResponse
import com.example.newsapp.feature_news.domain.repository.NewsRepository
import io.mockk.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetArticlesUseCaseTest {

    private val newsRepository: NewsRepository = mockk()
    private val getArticlesUseCase: GetArticlesUseCase = GetArticlesUseCase(newsRepository)

    @BeforeEach
    fun setUp() {
        clearMocks(newsRepository)
    }

    @Test
    fun `test newsRepository getTopNews call exactly once`(): Unit = runBlocking {
        val newsResponse = NewsResponse(
            articles = listOf(),
            "status",
            0
        )

        coEvery { newsRepository.getTopNews("us", 1) } returns newsResponse

        val l = getArticlesUseCase("us", 1)
            .take(2)
            .toList()

        coVerify(exactly = 1) { newsRepository.getTopNews("us", 1)}
    }

    @Test
    fun `test 2`(): Unit = runBlocking {
        val newsResponse = NewsResponse(
            articles = listOf(),
            "status",
            0
        )

        coEvery { newsRepository.getTopNews("us", 1) } returns newsResponse

        val l = getArticlesUseCase("us", 1)
            .take(2)
            .toList()

        coVerify(exactly = 1) { newsRepository.getTopNews("us", 1)}
    }


}