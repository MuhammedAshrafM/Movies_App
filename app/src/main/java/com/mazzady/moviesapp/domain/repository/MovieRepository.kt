package com.mazzady.moviesapp.domain.repository

import androidx.paging.PagingData
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun toggleFavorite(movieId: Int)
    suspend fun getMovieDetails(movieId: Int): MovieDetails?
}