package com.mazzady.moviesapp.data.mapper

import com.mazzady.moviesapp.data.local.entity.MovieDetailsEntity
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.remote.dto.MovieDetailsDto
import com.mazzady.moviesapp.data.remote.dto.MovieDto
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.model.MovieDetails

fun MovieDto.toMovieEntity(isFavorite: Boolean = false): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = poster_path,
        releaseDate = release_date,
        isFavorite = isFavorite,
        lastUpdated = System.currentTimeMillis()
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDetailsDto.toMovieDetailsEntity(isFavorite: Boolean = false): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        releaseDate = release_date,
        runtime = runtime,
        voteAverage = vote_average,
        genres = genres.joinToString(", ") { it.name },
        isFavorite = isFavorite,
        lastUpdated = System.currentTimeMillis()
    )
}

fun MovieDetailsEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        runtime = runtime,
        voteAverage = voteAverage,
        genres = genres.split(", "),
        isFavorite = isFavorite
    )
}