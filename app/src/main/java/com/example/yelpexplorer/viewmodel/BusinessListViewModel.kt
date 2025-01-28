package com.example.yelpexplorer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yelpexplorer.data.BusinessDao
import com.example.yelpexplorer.data.BusinessEntity
import com.example.yelpexplorer.data.YelpApiService
import com.example.yelpexplorer.model.Business
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessListViewModel @Inject constructor(
    private val yelpApi: YelpApiService,
    private val businessDao: BusinessDao
) : ViewModel() {
    //state to hold the list of business
    private val _businesses = MutableStateFlow<List<Business>>(emptyList())
    val businesses = _businesses.asStateFlow()

    //state to indicate the loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        observeLikedBusinesses()
    }

    //fetches the business for the specific city and update the state
    fun searchBusinesses(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //api call to fetch business
                val response = yelpApi.searchBusinesses(location = city)
                //fetch the liked business from database
                val likedBusinesses = businessDao.getAllLikedBusinesses().first()
                //check if business is liked
                _businesses.value = response.businesses.map { business ->
                    val isLiked = likedBusinesses.any { it.id == business.id }
                    business.copy(isLiked = isLiked)//update the isLiked field
                }
            } catch (e: Exception) {
                Log.e("BusinessListViewModel", "Error loading business list", e)
            } finally {
                _isLoading.value = false//reset the loading state
            }
        }
    }

    //observe the changes in liked business and update state
    private fun observeLikedBusinesses() {
        viewModelScope.launch {
            businessDao.getAllLikedBusinesses().collect { likedBusinesses ->
                val updatedBusinesses = _businesses.value.map { business ->
                    val isLiked = likedBusinesses.any { it.id == business.id }
                    business.copy(isLiked = isLiked)//isLiked business and datavasse changes synched accordingly
                }
                _businesses.value = updatedBusinesses
            }
        }
    }


    //toggle the liked state of the business
    fun toggleLike(businessId: String) {
        viewModelScope.launch {
            val business = _businesses.value.find { it.id == businessId } ?: return@launch
            if (business.isLiked) {
                //Remove the business if it is unliked
                businessDao.deleteLikedBusiness(
                    BusinessEntity(
                        id = business.id,
                        name = business.name,
                        imageUrl = business.imageUrl,
                        rating = business.rating
                    )
                )
            } else {
                //Add the business if it is liked
                businessDao.insertLikedBusiness(
                    BusinessEntity(
                        id = business.id,
                        name = business.name,
                        imageUrl = business.imageUrl,
                        rating = business.rating,
                        isLiked = true
                    )
                )
            }
            // Update UI state with new liked state
            _businesses.value = _businesses.value.map {
                if (it.id == businessId) it.copy(isLiked = !it.isLiked) else it
            }
        }
    }


}

