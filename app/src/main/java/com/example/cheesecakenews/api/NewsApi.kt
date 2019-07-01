package com.example.cheesecakenews.api

import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.model.Response
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface NewsApi {
    companion object {
        const val URL = "https://cheesecakelabs.com/"
    }

    @GET("challenge")
    fun news(
    ): Observable<List<News>>


}