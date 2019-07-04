package com.example.cheesecakenews.api

import com.example.cheesecakenews.model.News
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApi {
    companion object {
        const val URL = "https://cheesecakelabs.com/"
    }

    @GET("challenge")
    fun news(
    ): Observable<List<News>>
}