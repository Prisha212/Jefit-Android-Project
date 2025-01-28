package com.example.yelpexplorer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yelpexplorer.data.BusinessEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {
    //Retrieve all liked businesses from database as a Flow
    @Query("SELECT * FROM businesses")
    fun getAllLikedBusinesses(): Flow<List<BusinessEntity>>

    //check if a business like by its business id
    @Query("SELECT EXISTS (SELECT 1 FROM businesses WHERE id = :businessId)")
    fun isBusinessLiked(businessId: String): Flow<Boolean>

    //insert or replace business when it is liked
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedBusiness(business: BusinessEntity)

    //delete business from database when it is unliked
    @Delete
    suspend fun deleteLikedBusiness(business: BusinessEntity)
}