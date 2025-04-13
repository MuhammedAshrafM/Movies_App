package com.mazzady.moviesapp.domain.usecase

import androidx.paging.PagingData
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = repository.getMovies()
}