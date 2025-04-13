package com.mazzady.moviesapp.data.local.datasource

import androidx.paging.PagingSource
import com.mazzady.moviesapp.data.local.entity.FavoriteMovieEntity
import com.mazzady.moviesapp.data.local.entity.MovieDetailsEntity
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.local.entity.RemoteKeys
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insertMovies(movies: List<MovieEntity>)
    fun getMovies(): PagingSource<Int, MovieEntity>
    fun getFavoriteMovies(): Flow<List<MovieEntity>>
    suspend fun addFavorite(favorite: FavoriteMovieEntity)
    suspend fun removeFavorite(favorite: FavoriteMovieEntity)
    suspend fun isFavorite(movieId: Int): Boolean
    suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)
    suspend fun clearAll()
    suspend fun insertMovieDetails(details: MovieDetailsEntity)
    suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity?
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>)
    suspend fun remoteKeysByMovieId(movieId: Int): RemoteKeys?
    suspend fun clearOldCache()
    fun observeMovie(movieId: Int): Flow<MovieEntity?>
}