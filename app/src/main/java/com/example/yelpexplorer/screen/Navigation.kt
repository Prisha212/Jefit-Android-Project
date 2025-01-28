package com.example.yelpexplorer.screen

//This class contains all app navigation screen and routes
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object BusinessList : Screen("businesses/{city}") {
        fun createRoute(city: String) = "businesses/$city"
    }

    object BusinessDetail : Screen("business/{id}") {
        fun createRoute(id: String) = "business/$id"
    }
}

