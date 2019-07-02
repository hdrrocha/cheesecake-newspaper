package com.example.cheesecakenews.view_model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.example.cheesecakenews.SchedulerProvider
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.api.NewsApi
import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.view.news.adapter.NewsAdapter
import io.kotlintest.specs.Test
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.SocketException


class NewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiClient: ApiClient

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    lateinit var mainViewModel: NewsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = NewsViewModel(this.apiClient, this.schedulerProvider)
    }


    @Test
    fun wheneverFetchSuccessWithDataShouldResultSuccessWithData() {
        // Given
        val data = News("title", "website", "authors", "date","content", "image_url")
        val observable = Observable.just(data)

        // When
        Mockito.`when`(mainViewModel.fetchNews()).thenAnswer(null)


        // Then
        verify(observable, null)

    }
}


