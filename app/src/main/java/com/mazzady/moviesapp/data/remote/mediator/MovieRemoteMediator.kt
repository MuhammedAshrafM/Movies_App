package com.mazzady.moviesapp.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mazzady.moviesapp.data.remote.datasource.MovieRemoteDataSource
import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSource
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.local.entity.RemoteKeys
import com.mazzady.moviesapp.data.mapper.toMovieEntity
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val networkMonitor: NetworkMonitor
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (networkMonitor.isCurrentlyConnected()) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            if (!networkMonitor.isCurrentlyConnected()) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    nextKey
                }
            }

            val movies = remoteDataSource.getMovies(page)

            if (loadType == LoadType.REFRESH) {
                localDataSource.clearAll()
            }

            localDataSource.insertMovies(movies.map { it.toMovieEntity() })

            MediatorResult.Success(
                endOfPaginationReached = movies.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                // In this simple case, we're not using separate remote keys table
                // So we'll just return null or implement if needed
                null
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Similarly, return null or implement if needed
                null
            }
    }
}
