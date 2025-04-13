package com.mazzady.moviesapp.data.remote.datasource

import com.mazzady.moviesapp.data.remote.dto.MovieDetailsDto
import com.mazzady.moviesapp.data.remote.dto.MovieDto

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int): List<MovieDto>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDto
}