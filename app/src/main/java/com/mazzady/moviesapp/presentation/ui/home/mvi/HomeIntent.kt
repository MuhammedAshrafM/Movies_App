package com.mazzady.moviesapp.presentation.ui.home.mvi

sealed class HomeIntent {
    data object LoadMovies : HomeIntent()
    data class ToggleFavorite(val movieId: Int) : HomeIntent()
    data object ToggleLayout : HomeIntent()
    data object Retry : HomeIntent()
}