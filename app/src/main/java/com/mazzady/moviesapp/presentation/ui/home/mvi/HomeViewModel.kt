package com.mazzady.moviesapp.presentation.ui.home.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.usecase.GetMoviesUseCase
import com.mazzady.moviesapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    private val _isGridLayout = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout

    private val _currentPagingData = MutableStateFlow<PagingData<Movie>?>(null)
    val movies: StateFlow<PagingData<Movie>?> = _currentPagingData.asStateFlow()

    init {
        loadMovies()
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadMovies -> loadMovies()
            is HomeIntent.ToggleFavorite -> toggleFavorite(intent.movieId)
            HomeIntent.ToggleLayout -> toggleLayout()
            HomeIntent.Retry -> loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            getMoviesUseCase()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _currentPagingData.value = pagingData
                    _state.value = HomeState.Success(pagingData)
                }
        }
    }

    private fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
            _currentPagingData.value?.let {
                _state.value = HomeState.Success(it)
            }
        }
    }

    private fun toggleLayout() {
        _isGridLayout.value = !_isGridLayout.value
    }
}