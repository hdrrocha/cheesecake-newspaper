package com.example.cheesecakenews.view_model

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import android.util.Log
import com.example.cheesecakenews.SchedulerProvider
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.api.NewsApi
import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.model.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    val _data = MutableLiveData<List<News>>()
    val data: LiveData<List<News>> = _data


    fun fetchNews() {
        api.news().subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                _data.value = it
            }, {
                Log.e("ERROR", it.message)
            })
    }
}