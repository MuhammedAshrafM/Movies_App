package com.mazzady.moviesapp.presentation.ui.details.mvi

sealed class DetailsIntent {
    data object LoadMovieDetails : DetailsIntent()
    data object ToggleFavorite : DetailsIntent()
}