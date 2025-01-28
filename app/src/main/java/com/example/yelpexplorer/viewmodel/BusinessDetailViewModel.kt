package com.example.yelpexplorer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yelpexplorer.data.BusinessDao
import com.example.yelpexplorer.data.BusinessEntity
import com.example.yelpexplorer.data.YelpApiService
import com.example.yelpexplorer.model.BusinessDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessDetailViewModel @Inject constructor(
    private val yelpApi: YelpApiService,
    private val likedBusinessDao: BusinessDao
) : ViewModel() {
    //
    private val _businessDetails = MutableStateFlow<BusinessDetailsResponse?>(null)
    var businessDetails = _businessDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked = _isLiked.asStateFlow()

    private var currentBusinessId: String? = null

    //Load business for specific business id
    fun loadBusinessDetails(businessId: String) {
        currentBusinessId = businessId
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //api call to fetch business details
                val details = yelpApi.getBusinessDetails(businessId)
                //business details state is updated
                _businessDetails.value = details
                //Check if business is liked
                checkIfLiked(businessId)
            } catch (e: Exception) {
                Log.e("BusinessDetailViewModel", "Error loading business details", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    //check if the business with given id is liked
    private fun checkIfLiked(businessId: String) {
        viewModelScope.launch {
            likedBusinessDao.isBusinessLiked(businessId)
                .collect { isLiked ->
                    _isLiked.value = isLiked
                }
        }
    }

    //toggle like of the current business
    fun toggleLike() {
        viewModelScope.launch {
            val business = _businessDetails.value ?: return@launch
            //remove business from the liked list of database
            if (_isLiked.value) {
                likedBusinessDao.deleteLikedBusiness(
                    BusinessEntity(
                        id = business.id,
                        name = business.name,
                        imageUrl = business.imageUrl,
                        rating = business.rating


                    )
                )
            } else {
                //add business from the liked list of database
                likedBusinessDao.insertLikedBusiness(
                    BusinessEntity(
                        id = business.id,
                        name = business.name,
                        imageUrl = business.imageUrl,
                        rating = business.rating,
                        isLiked = true
                    )
                )
            }
            checkIfLiked(business.id)//update liked state after toggling
        }
    }


}