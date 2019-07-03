package com.example.cheesecakenews.view_model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.example.cheesecakenews.SchedulerProvider
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.data.NewsDBHelper
import com.example.cheesecakenews.model.News
import java.security.AccessControlContext
import javax.inject.Inject

class NewsNoticeViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    val _data = MutableLiveData<List<News>>()
    val data: LiveData<List<News>> = _data

    lateinit var newsDBHelper : NewsDBHelper

    fun fetchNewsNotice(title: String, context: Context ) {
        newsDBHelper = NewsDBHelper(context)
//        _data.value = newsDBHelper.readNews(title)
    }
}