package com.example.cheesecakenews.model

import com.google.gson.annotations.SerializedName

data class News (
    val title: String,
    val website: String,
    val authors: String,
    val date: String,
    val content: String,
    val image_url: String
)