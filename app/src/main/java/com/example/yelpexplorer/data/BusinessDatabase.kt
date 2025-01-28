package com.example.yelpexplorer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusinessEntity::class], version = 1)
abstract class BusinessDatabase : RoomDatabase() {
    //The DAO is used for database operation
    abstract fun businessDao(): BusinessDao


}