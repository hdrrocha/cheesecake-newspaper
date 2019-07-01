package com.example.cheesecakenews.view.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.cheesecakenews.R
import com.example.cheesecakenews.model.News
import com.example.cheesecakenews.model.Response
import com.example.cheesecakenews.view.news.adapter.NewsAdapter
import com.example.cheesecakenews.view_model.NewsViewModel
import com.example.cheesecakenews.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
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


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel.data.observe(this, ItemsObserver)
        newsViewModel.getNews()
    }


    private fun onItemsFetched(list: List<News>?) {
        if (list != null) {
            updateAdapter(list)
        }
    }

    private fun updateAdapter(list: List<News>) {
        recyclerViewNews.layoutManager = layoutManager
        recyclerViewNews.setHasFixedSize(true)

        if(listNewsView.size == 0){
            listNewsView = list as MutableList<News>
            adapter = NewsAdapter(listNewsView, { news: News -> partItemClicked(news) } )
            recyclerViewNews.adapter = adapter

        }else{
            listNewsView.addAll(list)
            adapter.notifyDataSetChanged()
            recyclerViewNews.adapter!!.notifyDataSetChanged()

        }
        progressBar.visibility = View.GONE
    }

    private fun partItemClicked(news: News) {
       //TO DO
    }

}
