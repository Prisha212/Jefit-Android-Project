package com.example.yelpexplorer.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yelpexplorer.ui.theme.YelpExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YelpExplorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    //navcontroller is created to manage navigation between screens
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        //Route to home screen
        composable(Screen.Home.route) {
            HomeScreen(
                onCitySelected = { city ->
                    //navigate to businesslist(i.e-second screen) with the city selected
                    navController.navigate(Screen.BusinessList.createRoute(city))
                }
            )
        }
        //Route to businesslist screen
        composable(
            Screen.BusinessList.route,
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: return@composable
            BusinessListScreen(
                city = city,
                onBusinessSelected = { businessId ->
                    //navigate to busineessdetails(i.r-third sreen) with business id selected
                    navController.navigate(Screen.BusinessDetail.createRoute(businessId))
                },
                onBackClick = { navController.popBackStack() }//handle the back navigation
            )
        }
        //Route to business details screen
        composable(
            Screen.BusinessDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val businessId = backStackEntry.arguments?.getString("id") ?: return@composable
            BusinessDetailScreen(
                businessId = businessId
            ) { navController.popBackStack() }//handle back navigation
        }
    }
}







