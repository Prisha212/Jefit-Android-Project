package com.example.yelpexplorer.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.yelpexplorer.viewmodel.BusinessDetailViewModel
import com.example.yelpexplorer.model.BusinessDetailsResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BusinessDetailScreen(
    viewModel: BusinessDetailViewModel = hiltViewModel(),
    businessId: String,
    onBackClick: () -> Unit
) {
    //Observe state from the Viewmodel
    val businessDetails by viewModel.businessDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLiked by viewModel.isLiked.collectAsState()

    LaunchedEffect(businessId) {
        viewModel.loadBusinessDetails(businessId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                //Business name is displayed in the top app bar
                title = { Text(businessDetails?.name ?: "") },
                navigationIcon = {
                    //Navigate back to previous screen
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    //Like/unlike action at the top app bar like toggle
                    IconButton(onClick = { viewModel.toggleLike() }) {
                        Icon(
                            if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Toggle Like"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                businessDetails?.let { business ->
                    BusinessDetailsContent(business = business)
                }
            }
        }
    }
}

@Composable
fun BusinessDetailsContent(
    business: BusinessDetailsResponse
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())//Enable the vetical scrolling if content is long
            .padding(16.dp)
    ) {
        //Business image is displayed
        AsyncImage(
            model = business.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Business name and details(i.e- rating,address,category)
        Text(text = business.name, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Rating: ${business.rating} ★", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "${business.location.address1}, ${business.location.city}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Categories: ${business.categories.joinToString { it.title }}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


