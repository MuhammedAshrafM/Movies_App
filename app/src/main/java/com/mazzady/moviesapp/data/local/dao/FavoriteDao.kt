package com.mazzady.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mazzady.moviesapp.data.local.entity.FavoriteMovieEntity
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteMovieEntity)

    @Query("DELETE FROM favorites")
    suspend fun clearAllFavorites()
}