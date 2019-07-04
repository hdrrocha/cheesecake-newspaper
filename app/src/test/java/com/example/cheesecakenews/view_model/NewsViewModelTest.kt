package com.example.cheesecakenews.view_model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cheesecakenews.SchedulerProvider
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.model.News
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiClient: ApiClient

    private val schedulerProvider: SchedulerProvider =
        object : SchedulerProvider {
            override fun mainThread(): Scheduler = Schedulers.trampoline()
            override fun io(): Scheduler = Schedulers.trampoline()
        }

    private lateinit var mainViewModel: NewsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = NewsViewModel(apiClient, schedulerProvider)
    }

    @Test
    fun `whenever fetch success with data it should result success with data`() {
        // Given
        val data = News("title", "website", "authors", "date", "content", "image_url", "r")
        `when`(apiClient.news()).thenReturn(Observable.just(listOf(data)))

        // When
        mainViewModel.fetchNews()

        // Then
        assertEquals(listOf(data), mainViewModel.data.value)
    }

    @Test
    fun `whenever fetch fails it should result in no data`() {
        // Given
        `when`(apiClient.news()).thenReturn(Observable.error(Exception("dummyError")))

        // When
        mainViewModel.fetchNews()

        // Then
        assertEquals(emptyList<News>(), mainViewModel.data.value)
    }
}


