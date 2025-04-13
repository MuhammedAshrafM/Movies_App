package com.mazzady.moviesapp.data.di

import android.content.Context
import com.mazzady.moviesapp.presentation.common.ConnectivityManagerNetworkMonitor
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor {
        return ConnectivityManagerNetworkMonitor(
            context = context
        )
    }

}