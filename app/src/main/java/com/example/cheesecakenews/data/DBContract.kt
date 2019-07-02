package com.example.cheesecakenews.data

import android.provider.BaseColumns

object DBContract {
    class NewsEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "news"
            val COLUMN_TITLE = "title"
            val COLUMN_WEBSITE = "website"
            val COLUMN_AUTHORS = "authors"
            val COLUMN_DATE = "date"
            val COLUMN_CONTENT = "content"
            val COLUMN_IMAGE_URL = "image_url"
        }
    }
}
