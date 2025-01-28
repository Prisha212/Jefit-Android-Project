package com.example.yelpexplorer.model

import com.google.gson.annotations.SerializedName

data class BusinessSearchResponse(
    @SerializedName("businesses") val businesses: List<Business>,
    @SerializedName("total") val total: Int
)