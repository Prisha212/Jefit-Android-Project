package com.example.yelpexplorer.model

import com.google.gson.annotations.SerializedName

data class Business(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("rating") val rating: Double,
    var isLiked: Boolean = false
)