package com.example.yelpexplorer.di

import com.example.yelpexplorer.BuildConfig
import com.example.yelpexplorer.data.YelpApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                //added a authorization heder with yelp api key to every request
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.YELP_API_KEY}")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    //used the loginterceptor for http request and response
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")//base url for the yelp's api
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())//convert the JSON response to the kotlin object
            .build()
    }

    @Provides
    @Singleton
    fun provideYelpApiService(retrofit: Retrofit): YelpApiService {
        return retrofit.create(YelpApiService::class.java)
    }
}