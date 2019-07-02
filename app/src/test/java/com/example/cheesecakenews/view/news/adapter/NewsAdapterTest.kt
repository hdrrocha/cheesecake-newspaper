package com.example.cheesecakenews.view.news.adapter

import com.example.cheesecakenews.model.News
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsAdapterTest {
    @Spy
    val adapter = NewsAdapter(null)
    @Test
    fun should_UpdateNewsLine_WhenReceiveNewsList() {
        doNothing().`when`(adapter).updateList()

        val newsList: List<News>
        newsList = arrayListOf(
            News("title", "website", "authors", "date","content", "image_url"),

            News("title2", "website", "authors", "date","content", "image_url"))
        adapter.update(newsList)

        verify(adapter).updateList()
        assertThat( adapter.itemCount, `is`<Int>(2))
    }
}