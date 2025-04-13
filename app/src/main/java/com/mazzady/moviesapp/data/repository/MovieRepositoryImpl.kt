package com.mazzady.moviesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSource
import com.mazzady.moviesapp.data.local.entity.FavoriteMovieEntity
import com.mazzady.moviesapp.data.mapper.toMovie
import com.mazzady.moviesapp.data.mapper.toMovieDetails
import com.mazzady.moviesapp.data.mapper.toMovieDetailsEntity
import com.mazzady.moviesapp.data.remote.datasource.MovieRemoteDataSource
import com.mazzady.moviesapp.data.remote.mediator.MovieRemoteMediator
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.model.MovieDetails
import com.mazzady.moviesapp.domain.repository.MovieRepository
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        remoteMediator = MovieRemoteMediator(
            remoteDataSource,
            localDataSource,
            networkMonitor
        ),
        pagingSourceFactory = { localDataSource.getMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toMovie() }
    }.flowOn(dispatcher)

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies()
            .map { movies -> movies.map { it.toMovie() } }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val isFavorite = localDataSource.isFavorite(movieId)
        if (isFavorite) {
            localDataSource.removeFavorite(FavoriteMovieEntity(movieId = movieId))
        } else {
            localDataSource.addFavorite(FavoriteMovieEntity(movieId = movieId))
        }
        localDataSource.updateFavoriteStatus(movieId, !isFavorite)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        return try {
            // Try to fetch fresh data
            if (networkMonitor.isCurrentlyConnected()) {
                val remoteDetails = remoteDataSource.getMovieDetails(movieId)
                val isFavorite = localDataSource.isFavorite(movieId)
                val detailsEntity = remoteDetails.toMovieDetailsEntity(isFavorite)
                localDataSource.insertMovieDetails(detailsEntity)
                detailsEntity.toMovieDetails()
            } else {
                // Fall back to local data when offline
                localDataSource.getMovieDetails(movieId)?.toMovieDetails()
                    ?: throw Exception("No cached data available")
            }
        } catch (e: Exception) {
            // Fall back to local data if available
            localDataSource.getMovieDetails(movieId)?.toMovieDetails()
        }
    }
}