package com.mazzady.moviesapp.presentation.ui.details.mvi

import com.mazzady.moviesapp.domain.model.MovieDetails

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val movieDetails: MovieDetails) : DetailsState()
    data class Error(val message: String) : DetailsState()
}