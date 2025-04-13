package com.mazzady.moviesapp.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val genres: List<String>,
    val isFavorite: Boolean
)