package com.mazzady.moviesapp.data.remote.datasource

import com.mazzady.moviesapp.data.remote.api.MovieApiService
import com.mazzady.moviesapp.data.remote.dto.MovieDetailsDto
import com.mazzady.moviesapp.data.remote.dto.MovieDto
import com.mazzady.moviesapp.data.remote.util.handleApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRemoteDataSource {

    override suspend fun getMovies(page: Int): List<MovieDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.discoverMovies(page)
                response.results ?: emptyList()
            } catch (e: Exception) {
                throw handleApiError(e)
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getMovieDetails(movieId)
            } catch (e: Exception) {
                throw handleApiError(e)
            }
        }
    }

}