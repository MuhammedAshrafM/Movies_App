package com.mazzady.moviesapp.data.remote.dto

data class MovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val overview: String?
)