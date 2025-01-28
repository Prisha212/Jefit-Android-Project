# Yelp Explorer - Android App

Yelp Explorer is an Android application that allows users to search for and explore businesses using the Yelp Fusion API. The app provides a clean and intuitive user interface, built using Jetpack Compose, and follows the MVVM architectural pattern.

## Features

1. **Home Screen**: The home screen displays a list of five cities that the user can select from. Clicking on a city will navigate the user to the business list screen.

2. **Business List Screen**: The business list screen displays a maximum of 10 businesses for the selected city. Each business is presented with its name, thumbnail image, and an indication of whether the business has been liked or not. Clicking on a business will navigate the user to the business details screen.

3. **Business Details Screen**: The business details screen provides comprehensive information about the selected business, including the name, rating, address, categories, and a thumbnail image. Users can like or unlike the business, and the liked status is persisted across searches using a Room database.

## Technologies and component 

- **Jetpack Compose**: The app's user interface is built using the Jetpack Compose framework.
- **MVVM Architecture**: The app follows the Model-View-ViewModel (MVVM) architectural pattern.
- **Dagger Hilt**: Dependency injection is handled using Dagger Hilt.
- **Room Database**: The app uses the Room database library to persist liked businesses.
- **Retrofit**: The app utilizes Retrofit to make API calls to the Yelp Fusion API.
- **Navigation Compose**: The app uses the Navigation Compose library to handle navigation between screens.
- **Coroutines and StateFlow**: The app uses Kotlin coroutines and StateFlow for asynchronous operations and state management.

## Getting Started

1. **Clone the Repository**: Clone the Jefit Android Project repository.
2. **Build and Run**: Open the project in Android Studio and build the app. Then, run the app on an Android device or emulator.

   **Home Screen**
![image](https://github.com/user-attachments/assets/5979d2e4-339a-4938-bdd1-9fa8d50e4f2f)

 **Business List Screen**
![image](https://github.com/user-attachments/assets/220f6c76-4045-4b2c-ab70-b491ff9645cf)

**Business Details Screen**
![image](https://github.com/user-attachments/assets/6e08f801-ce3a-41ab-95d2-7bf8e73a3c76)



