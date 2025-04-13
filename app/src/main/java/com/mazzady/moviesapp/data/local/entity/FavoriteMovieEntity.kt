package com.mazzady.moviesapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [Index(value = ["movieId"], unique = true)]
)
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val movieId: Int,
    val addedAt: Long = System.currentTimeMillis()
)