package com.mazzady.moviesapp.domain.usecase

import com.mazzady.moviesapp.domain.model.MovieDetails
import com.mazzady.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails? {
        return repository.getMovieDetails(movieId)
    }
}