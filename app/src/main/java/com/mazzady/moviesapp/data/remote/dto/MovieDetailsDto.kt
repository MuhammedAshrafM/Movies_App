package com.mazzady.moviesapp.data.remote.dto

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val runtime: Int,
    val vote_average: Double,
    val genres: List<GenreDto>,
    val status: String
)