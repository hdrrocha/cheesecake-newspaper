package com.example.cheesecakenews.view.news_item

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.cheesecakenews.R
import com.example.cheesecakenews.data.NewsDBHelper
import com.example.cheesecakenews.model.News
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_notice.*

class NewsItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_notice)
        try {
            val news = requireNotNull(intent.getNews())
            showNews(news)
        } catch (_: IllegalArgumentException) {
            onBackPressed()
        }
    }

    companion object {
        private fun Intent.getNews(): News? =
            getSerializableExtra("notice") as? News
    }

    fun showNews(news: News?) {
        textViewNewsTitle.text = news?.title
        Glide.with(this)
            .load(news?.image_url)
            .into(imageViewNewsThumb)
        textViewNewsContent.text = news?.content
        configureButton(news)
    }

    private fun configureButton(newsNotice: News?) {
        if(newsNotice?.is_read.equals("r")) {
            buttonReadUnread.text = getString(R.string.mark_unread)
        } else {
            buttonReadUnread.text = getString(R.string.mark_read)
        }

        buttonReadUnread.setOnClickListener {
            if(newsNotice?.is_read.equals("r")) {
                if (newsNotice != null) {
                    readUnread("n", newsNotice)
                }
            } else {
                if (newsNotice != null) {
                    readUnread("r", newsNotice)
                }
            }
        }
    }

    private fun readUnread(s: String, news: News) {
        news?.is_read = s
        val newsDBHelper = NewsDBHelper(this)
        newsDBHelper.readItem(news)
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down)
    }
}
