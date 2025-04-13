package com.mazzady.moviesapp.domain.usecase

import com.mazzady.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.toggleFavorite(movieId)
    }
}