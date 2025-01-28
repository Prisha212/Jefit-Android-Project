package com.example.yelpexplorer.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Enum class to display list of cities
enum class Cities(val displayName: String) {
    NEW_YORK("New York"),
    LOS_ANGELES("Los Angeles"),
    CHICAGO("Chicago"),
    SAN_FRANCISCO("San Francisco"),
    MIAMI("Miami");

    companion object {
        //helper method is used to get list of all city
        fun getAllCities() = values().map { it.displayName }
    }

}

@Composable
fun HomeScreen(
    onCitySelected: (String) -> Unit //when city is selected, callback triggered
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            //header text to select a city
            text = "Select a City",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        //List of city displayed using lazycolumn
        LazyColumn {
            //helper method used here to get all city name
            items(Cities.getAllCities()) { city ->
                CityCard(
                    city = city,
                    onClick = { onCitySelected(city) }//selected city is passed to callback
                )
            }
        }
    }
}

@Composable
fun CityCard(
    city: String,
    onClick: () -> Unit
) {
    //Card UI to display city
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        //City name is displayed inside the card
        Text(
            text = city,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}