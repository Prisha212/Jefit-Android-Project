package com.example.yelpexplorer.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.yelpexplorer.viewmodel.BusinessListViewModel
import com.example.yelpexplorer.model.Business

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessListScreen(
    viewModel: BusinessListViewModel = hiltViewModel(),
    city: String,
    onBusinessSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    //The list of business and loading state is observed from the viewModel
    val businesses by viewModel.businesses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(city) {
        viewModel.searchBusinesses(city)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                //City name is dispalyed at the top app bar
                title = { Text(city) },
                navigationIcon = {
                    //Back Button is used to moe to previous screen
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                //Loading indicator at the middle of the screen by the time data is loaded
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                //List of business is displayed using lazy column
                LazyColumn {
                    items(businesses) { business ->
                        //Each business is displayd on the card
                        BusinessCard(
                            business = business,
                            onBusinessClick = { onBusinessSelected(business.id) },
                            onLikeClick = { viewModel.toggleLike(business.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    business: Business,
    onBusinessClick: () -> Unit,
    onLikeClick: () -> Unit,
) {
    //Card for each business item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onBusinessClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Load and Display the business image
            AsyncImage(
                model = business.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                //show business and rating
                Text(text = business.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${business.rating} ★", style = MaterialTheme.typography.bodyMedium)
            }
            // Like button with icon and color
            IconButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (business.isLiked) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    contentDescription = if (business.isLiked) "Unlike" else "Like",
                    tint = if (business.isLiked) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}