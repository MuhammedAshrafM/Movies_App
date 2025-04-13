
# MovieApp

This Android movie app allows users to discover the latest and most popular movies with detailed information such as Movie name, rating, original language, vote average, and poster with a user-friendly interface. The app is built using Kotlin and follows the Model-View-Intent (MVI) architecture pattern to ensure a clear separation of concerns, making the codebase more manageable and scalable.

## Features

- Discover Movies: Browse through the latest and trending movies with detailed information.
- Movie Details: View detailed information about movies including Movie name, rating, release dates, original language, vote average, and poster.
- Favorite Movies Screen: View a list of all your favorite movies.
- Change List View: Switch between linear and grid layouts for the movie list.
- Follows MVI architecture pattern.
- Uses Retrofit to fetch data from The Movie DB API.
- Uses background threads rather than main threads to ensure a smooth user experience by using coroutines.
- Save the favorites in the local database by Room.


## Technologies Used
- Kotlin: Leveraging modern programming practices and features for a robust and concise codebase.
- MVI Architecture: Ensuring a unidirectional data flow for better state management and maintainability.
- Retrofit: Efficiently making API requests to The Movie Database (TMDB) for fetching movie data.
- Coroutines: Handling asynchronous operations smoothly for a responsive user experience.
- ViewModel and StateFlow: Providing a robust way to manage state and UI updates in a lifecycle-aware.
- Hilt: Simplifying dependency injection with a standardized framework.
- Room: Providing an abstraction layer over SQLite for efficient local data.
- Coil3: Loading and caching images seamlessly.
- **Paging 3** for efficient list loading


## Installation

To get a local copy up and running, follow these simple steps:

1. **Clone the repository**
- git clone https://github.com/MuhammedAshrafM/Movies_App.git
2. **Open the project in Android Studio**
3. **Build the project**
- Android Studio will prompt you to install the necessary dependencies.
4. **Run the app**
- Connect your Android device or use an emulator to run the app.

    
## Usage
- Launch the app on your Android device.
- Browse through the latest and most popular movies.
- View detailed information about each movie, including ratings and release dates.
- Save your favorite movies for quick access later.


## API Reference

- This app uses the The Movie Database(TMDB) API to fetch news data. 
- API Documentation:
    https://developer.themoviedb.org/docs/getting-started.
