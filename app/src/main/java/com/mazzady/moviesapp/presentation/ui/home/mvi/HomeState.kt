package com.mazzady.moviesapp.presentation.ui.home.mvi

import androidx.paging.PagingData
import com.mazzady.moviesapp.domain.model.Movie

sealed class HomeState {
    data class Success(val movies: PagingData<Movie>) : HomeState()
    data class Error(val message: String) : HomeState()
    data object Loading : HomeState()
    data object Empty : HomeState()
}