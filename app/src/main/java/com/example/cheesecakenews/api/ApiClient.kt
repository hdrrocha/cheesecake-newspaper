package com.example.cheesecakenews.api

import com.example.cheesecakenews.model.News
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(private val newsApi: NewsApi) {
    fun news(): Observable<List<News>> {
        return newsApi.news()
    }
}