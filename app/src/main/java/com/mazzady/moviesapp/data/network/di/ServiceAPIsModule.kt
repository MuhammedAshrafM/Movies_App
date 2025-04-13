package com.mazzady.moviesapp.data.network.di

import com.mazzady.moviesapp.data.remote.api.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceAPIsModule {

    @Singleton
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit.Builder): MovieApiService =
        retrofit.build().create(MovieApiService::class.java)

}