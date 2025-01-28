package com.example.yelpexplorer.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("alias") val alias: String,
    @SerializedName("title") val title: String
)