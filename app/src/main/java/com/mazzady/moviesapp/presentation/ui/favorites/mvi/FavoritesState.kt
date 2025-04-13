package com.mazzady.moviesapp.presentation.ui.favorites.mvi

import com.mazzady.moviesapp.domain.model.Movie

sealed class FavoritesState {
    data object Loading : FavoritesState()
    data object Empty : FavoritesState()
    data class Success(val movies: List<Movie>) : FavoritesState()
}