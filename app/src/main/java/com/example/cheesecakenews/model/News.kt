package com.example.cheesecakenews.model

import java.io.Serializable

data class News(
    val title: String,
    val website: String,
    val authors: String,
    val date: String,
    val content: String,
    val image_url: String,
    var is_read: String?
) :Serializable