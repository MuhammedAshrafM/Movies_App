package com.mazzady.moviesapp.presentation.ui.favorites.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazzady.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import com.mazzady.moviesapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        processIntent(FavoritesIntent.LoadFavorites)
    }

    fun processIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.ToggleFavorite -> toggleFavorite(intent.movieId)
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoritesState.Loading
            try {
                getFavoritesUseCase().collect { movies ->
                    _state.value = if (movies.isEmpty()) {
                        FavoritesState.Empty
                    } else {
                        FavoritesState.Success(movies)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
            loadFavorites()
        }
    }
}