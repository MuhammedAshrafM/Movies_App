package com.mazzady.moviesapp.presentation.ui.details.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazzady.moviesapp.domain.usecase.GetMovieDetailsUseCase
import com.mazzady.moviesapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        processIntent(DetailsIntent.LoadMovieDetails)
    }


    fun processIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadMovieDetails -> loadMovieDetails()
            is DetailsIntent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            (_state.value as? DetailsState.Success)?.let { currentState ->
                toggleFavoriteUseCase(currentState.movieDetails.id)
                _state.value = currentState.copy(
                    movieDetails = currentState.movieDetails.copy(
                        isFavorite = !currentState.movieDetails.isFavorite
                    )
                )
            }
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _state.value = DetailsState.Loading
            try {
                val details = getMovieDetailsUseCase(movieId)
                details?.let {
                    _state.value = DetailsState.Success(details)
                }
            } catch (e: Exception) {
                _state.value = DetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun retry() {
        loadMovieDetails()
    }
}