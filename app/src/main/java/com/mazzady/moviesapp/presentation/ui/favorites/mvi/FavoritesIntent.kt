package com.mazzady.moviesapp.presentation.ui.favorites.mvi

sealed class FavoritesIntent {
    data object LoadFavorites : FavoritesIntent()
    data class ToggleFavorite(val movieId: Int) : FavoritesIntent()
}