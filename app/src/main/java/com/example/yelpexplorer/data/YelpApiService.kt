package com.example.yelpexplorer.data

import com.example.yelpexplorer.model.BusinessDetailsResponse
import com.example.yelpexplorer.model.BusinessSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpApiService {
    @GET("businesses/search")
    suspend fun searchBusinesses(
        @Query("location") location: String,
        @Query("limit") limit: Int = 10
    ): BusinessSearchResponse

    @GET("businesses/{id}")
    suspend fun getBusinessDetails(
        @Path("id") id: String
    ): BusinessDetailsResponse
}