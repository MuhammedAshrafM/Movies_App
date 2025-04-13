package com.mazzady.moviesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mazzady.moviesapp.data.local.dao.FavoriteDao
import com.mazzady.moviesapp.data.local.dao.MovieDao
import com.mazzady.moviesapp.data.local.dao.MovieDetailsDao
import com.mazzady.moviesapp.data.local.dao.RemoteKeysDao
import com.mazzady.moviesapp.data.local.entity.FavoriteMovieEntity
import com.mazzady.moviesapp.data.local.entity.MovieDetailsEntity
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.local.entity.RemoteKeys
import java.util.Date

@Database(
    entities = [
        MovieEntity::class,
        FavoriteMovieEntity::class,
        MovieDetailsEntity::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}