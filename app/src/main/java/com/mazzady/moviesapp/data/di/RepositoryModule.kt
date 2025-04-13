package com.mazzady.moviesapp.data.di

import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSourceImpl
import com.mazzady.moviesapp.data.remote.datasource.MovieRemoteDataSourceImpl
import com.mazzady.moviesapp.data.repository.MovieRepositoryImpl
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        moviesRemoteDataSource: MovieRemoteDataSourceImpl,
        moviesLocalDataSource: MovieLocalDataSourceImpl,
        networkMonitor: NetworkMonitor,
    ) = MovieRepositoryImpl(
        remoteDataSource = moviesRemoteDataSource,
        localDataSource = moviesLocalDataSource,
        networkMonitor = networkMonitor,
    )

}