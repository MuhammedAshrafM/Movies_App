package com.mazzady.moviesapp.presentation.common

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
    fun isCurrentlyConnected(): Boolean
}