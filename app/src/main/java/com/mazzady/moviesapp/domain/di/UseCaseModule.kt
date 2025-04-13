package com.mazzady.moviesapp.domain.di

import com.mazzady.moviesapp.data.repository.MovieRepositoryImpl
import com.mazzady.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import com.mazzady.moviesapp.domain.usecase.GetMovieDetailsUseCase
import com.mazzady.moviesapp.domain.usecase.GetMoviesUseCase
import com.mazzady.moviesapp.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetMoviesUseCase(repository: MovieRepositoryImpl) =
        GetMoviesUseCase(repository)

    @Singleton
    @Provides
    fun provideGetFavoriteMoviesUseCase(repository: MovieRepositoryImpl) =
        GetFavoriteMoviesUseCase(repository)


    @Singleton
    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieRepositoryImpl) =
        GetMovieDetailsUseCase(repository)


    @Singleton
    @Provides
    fun provideToggleFavoriteUseCase(repository: MovieRepositoryImpl) =
        ToggleFavoriteUseCase(repository)

}