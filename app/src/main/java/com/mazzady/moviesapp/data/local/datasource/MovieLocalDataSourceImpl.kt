package com.mazzady.moviesapp.data.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.mazzady.moviesapp.data.local.MovieDatabase
import com.mazzady.moviesapp.data.local.entity.FavoriteMovieEntity
import com.mazzady.moviesapp.data.local.entity.MovieDetailsEntity
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.local.entity.RemoteKeys
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val database: MovieDatabase
) : MovieLocalDataSource {
    private val movieDao = database.movieDao()
    private val favoriteDao = database.favoriteDao()
    private val movieDetailsDao = database.movieDetailsDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        database.withTransaction {
            movieDao.insertMovies(movies)
        }
    }

    override fun getMovies(): PagingSource<Int, MovieEntity> {
        return movieDao.getAllMovies()
    }

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMoviesFlow()
    }

    override suspend fun addFavorite(favorite: FavoriteMovieEntity) {
        database.withTransaction {
            favoriteDao.insertFavorite(favorite)
            movieDao.updateFavoriteStatus(favorite.movieId, true)
            movieDetailsDao.updateFavoriteStatus(favorite.movieId, true)
        }
    }

    override suspend fun removeFavorite(favorite: FavoriteMovieEntity) {
        database.withTransaction {
            favoriteDao.deleteFavorite(favorite)
            movieDao.updateFavoriteStatus(favorite.movieId, false)
            movieDetailsDao.updateFavoriteStatus(favorite.movieId, false)
        }
    }


    override suspend fun isFavorite(movieId: Int): Boolean {
        return movieDao.isFavorite(movieId)
    }

    override suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        database.withTransaction {
            movieDao.updateFavoriteStatus(movieId, isFavorite)
            movieDetailsDao.updateFavoriteStatus(movieId, isFavorite)
        }
    }


    override suspend fun clearAll() {
        database.withTransaction {
            movieDao.deleteAllMovies()
            favoriteDao.clearAllFavorites()
            remoteKeysDao.clearRemoteKeys()
        }
    }

    override suspend fun insertMovieDetails(details: MovieDetailsEntity) {
        database.withTransaction {
            movieDetailsDao.insert(details)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity? {
        return movieDetailsDao.getMovieDetails(movieId)
    }

    override suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) {
        remoteKeysDao.insertAll(remoteKeys)
    }

    override suspend fun remoteKeysByMovieId(movieId: Int): RemoteKeys? {
        return remoteKeysDao.remoteKeysByMovieId(movieId)
    }

    override suspend fun clearOldCache() {
        val oneHourAgo = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1)
        database.withTransaction {
            movieDao.deleteOldMovies(oneHourAgo)
            movieDetailsDao.deleteOldDetails(oneHourAgo)
        }
    }

    override fun observeMovie(movieId: Int): Flow<MovieEntity?> {
        return movieDao.observeMovieById(movieId)
    }
}