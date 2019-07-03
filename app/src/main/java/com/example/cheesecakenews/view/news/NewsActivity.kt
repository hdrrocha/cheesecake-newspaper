package com.example.cheesecakenews.view.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.cheesecakenews.R
import com.example.cheesecakenews.data.NewsDBHelper
import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.view.NewsNotice.NewsNoticeActivity
import com.example.cheesecakenews.view.news.adapter.NewsAdapter
import com.example.cheesecakenews.view_model.NewsViewModel
import com.example.cheesecakenews.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {
    @Inject
    lateinit var newsVMFactory: ViewModelFactory<NewsViewModel>

    private val newsViewModel by lazy {
        ViewModelProviders.of(this, newsVMFactory)[NewsViewModel::class.java]
    }

    protected val ItemsObserver = Observer<List<News>>(::onItemsFetched)

    private lateinit var adapter: NewsAdapter
    var layoutManager = LinearLayoutManager(this)
    var listNewsView: MutableList<News> = mutableListOf()

    lateinit var newsDBHelper : NewsDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsDBHelper = NewsDBHelper(this)
        if (verifyAvailableNetwork(this)) {
            newsViewModel.data.observe(this, ItemsObserver)
            newsViewModel.fetchNews()
        } else {
            listNewsView = newsDBHelper.readAllNews()
            updateAdapter(listNewsView)
        }
    }


    private fun onItemsFetched(list: List<News>?) {
        if (list != null) {
            list.forEach {
                addNews(it)
            }
            updateAdapter(list)
        }
    }

    fun addNews(news: News){
        var result = newsDBHelper.insertNews(News(news.title, news.website, news.authors, news.content, news.date, news.image_url, news.is_read))
    }

    private fun updateAdapter(list: List<News>) {
        recyclerViewNews.layoutManager = layoutManager
        recyclerViewNews.setHasFixedSize(true)
        adapter = NewsAdapter({ news: News -> partItemClicked(news) } )
        adapter.update(list)
        recyclerViewNews.adapter = adapter
        progressBar.visibility = View.GONE
    }

    private fun partItemClicked(news: News) {
        val intent = Intent(this.baseContext, NewsNoticeActivity::class.java)
        intent.putExtra("notice", news as Serializable)
        news.is_read = "r"
        newsDBHelper.readItem(news)
        startActivity(intent)
    }

    fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}
