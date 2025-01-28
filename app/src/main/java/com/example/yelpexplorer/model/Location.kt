package com.example.yelpexplorer.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address1") val address1: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zip_code") val zipCode: String
)