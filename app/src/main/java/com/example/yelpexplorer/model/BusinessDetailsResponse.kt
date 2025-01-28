package com.example.yelpexplorer.model

import com.google.gson.annotations.SerializedName

data class BusinessDetailsResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("phone") val phone: String,
    @SerializedName("location") val location: Location,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("review_count") val reviewCount: Int
)