package com.mazzady.moviesapp.data.remote.util


fun handleApiError(exception: Exception): Exception {
    return when (exception) {
        is retrofit2.HttpException -> {
            when (exception.code()) {
                401 -> Exception("Invalid API key")
                404 -> Exception("Movie not found")
                429 -> Exception("Rate limit exceeded")
                else -> Exception("Network error occurred")
            }
        }
        is java.net.SocketTimeoutException -> Exception("Connection timeout")
        is java.net.UnknownHostException -> Exception("No internet connection")
        else -> Exception("Unknown error occurred")
    }
}