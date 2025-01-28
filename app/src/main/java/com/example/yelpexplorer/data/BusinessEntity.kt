package com.example.yelpexplorer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class BusinessEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val isLiked: Boolean = false
)