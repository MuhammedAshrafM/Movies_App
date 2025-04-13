package com.mazzady.moviesapp.data.di

import com.mazzady.moviesapp.data.local.MovieDatabase
import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSourceImpl
import com.mazzady.moviesapp.data.remote.api.MovieApiService
import com.mazzady.moviesapp.data.remote.datasource.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        apiService: MovieApiService
    ) = MovieRemoteDataSourceImpl(
        apiService = apiService
    )

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(
        movieDatabase: MovieDatabase
    ) = MovieLocalDataSourceImpl(
        database = movieDatabase
    )

}