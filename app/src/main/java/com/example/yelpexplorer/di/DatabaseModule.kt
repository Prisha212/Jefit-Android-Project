package com.example.yelpexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.yelpexplorer.data.BusinessDao
import com.example.yelpexplorer.data.BusinessDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideBusinessDatabase(@ApplicationContext context: Context): BusinessDatabase {
        return Room.databaseBuilder(
            context,
            BusinessDatabase::class.java,//database class
            "business_database" //name of the database
        ).build()
    }

    @Provides
    @Singleton
    fun provideBusinessDao(database: BusinessDatabase): BusinessDao {
        return database.businessDao()
    }
}