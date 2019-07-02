package com.example.cheesecakenews.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.cheesecakenews.model.News


class NewsDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertNews(news: News): Boolean {

        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.NewsEntry.COLUMN_TITLE, news.title)
        values.put(DBContract.NewsEntry.COLUMN_WEBSITE, news.website)
        values.put(DBContract.NewsEntry.COLUMN_AUTHORS, news.authors)
        values.put(DBContract.NewsEntry.COLUMN_CONTENT, news.content)
        values.put(DBContract.NewsEntry.COLUMN_DATE, news.date)
        values.put(DBContract.NewsEntry.COLUMN_IMAGE_URL, news.image_url)

        val newRowId = db.insert(DBContract.NewsEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteNews(newsTitle: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.NewsEntry.COLUMN_TITLE + " LIKE ?"
        val selectionArgs = arrayOf(newsTitle)
        db.delete(DBContract.NewsEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readNews(newsTitle: String): ArrayList<News> {
        val news = ArrayList<News>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.NewsEntry.TABLE_NAME + " WHERE " + DBContract.NewsEntry.COLUMN_TITLE + "='" + newsTitle + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var title: String
        var website: String
        var authors: String
        var date: String
        var content: String
        var image_url: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_TITLE))
                website = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_WEBSITE))
                authors = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_AUTHORS))
                date = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_DATE))
                content = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_CONTENT))
                image_url = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_IMAGE_URL))

                news.add(News(title, website, authors, date, content, image_url))
                cursor.moveToNext()
            }
        }
        return news
    }

    fun readAllNews(): ArrayList<News> {
        val news = ArrayList<News>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.NewsEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var title: String
        var website: String
        var authors: String
        var date: String
        var content: String
        var image_url: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_TITLE))
                website = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_WEBSITE))
                authors = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_AUTHORS))
                date = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_DATE))
                content = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_CONTENT))
                image_url = cursor.getString(cursor.getColumnIndex(DBContract.NewsEntry.COLUMN_IMAGE_URL))

                news.add(News(title, website, authors, date, content, image_url))
                cursor.moveToNext()
            }
        }
        return news
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.NewsEntry.TABLE_NAME + " (" +
                    DBContract.NewsEntry.COLUMN_TITLE + " TEXT PRIMARY KEY," +
                    DBContract.NewsEntry.COLUMN_AUTHORS + " TEXT," +
                    DBContract.NewsEntry.COLUMN_WEBSITE + " TEXT," +
                    DBContract.NewsEntry.COLUMN_CONTENT + " TEXT," +
                    DBContract.NewsEntry.COLUMN_DATE + " TEXT," +
                    DBContract.NewsEntry.COLUMN_IMAGE_URL + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.NewsEntry.TABLE_NAME
    }

}