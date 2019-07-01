package com.example.cheesecakenews.api

import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.model.Response
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

class ApiClient @Inject constructor(private val newsApi: NewsApi) {
    fun news(): Observable<List<News>> {
        return newsApi.news()
    }
}