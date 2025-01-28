package com.example.yelpexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
//This class is entry point of hilt for hilt dependency injection
class YelpExplorerApp : Application()