package com.example.cheesecakenews.view.NewsNotice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.cheesecakenews.R
import com.example.cheesecakenews.data.NewsDBHelper
import com.example.cheesecakenews.model.News
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_notice.*

class NewsNoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_notice)
        showNotice( createNews(getIntent().getExtras()))
    }

    fun createNews(extras: Bundle?): News? {
        return  extras?.getSerializable("notice") as? News
    }


    fun showNotice(newsNotice: News?) {
        textViewNewsTitle.text = newsNotice?.title
        Glide.with(this)
            .load("${newsNotice?.image_url}")
            .into(imageViewNewsThumb)
        textViewNewsContent.text = newsNotice?.content
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down)
    }
}
