package com.example.cheesecakenews.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import android.util.Log
import com.example.cheesecakenews.SchedulerProvider
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.model.News
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    private val _data = MutableLiveData<List<News>>()
    val data: LiveData<List<News>> = _data
    private val disposable = CompositeDisposable()
    fun fetchNews() {
        disposable.add(
            api.news().subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                _data.value = it
            }, {
                _data.value = emptyList()
                Log.e("ERROR", it.message)
            }))
    }
}