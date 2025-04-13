package com.mazzady.moviesapp.data

import com.mazzady.moviesapp.data.local.MovieDatabase
import com.mazzady.moviesapp.data.local.dao.MovieDao
import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSourceImpl
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieLocalDataSourceImplTest {

    private val database: MovieDatabase = mockk()
    private val dataSource = MovieLocalDataSourceImpl(database)

    @Test
    fun `getFavoriteMovies should return flow from dao`() = runTest {
        // Arrange
        val testEntities = listOf(
            MovieEntity(id = 1, title = "Movie 1", overview = "", posterPath = "", releaseDate = "", isFavorite = true)
        )
        every { database.movieDao().getFavoriteMoviesFlow() } returns flowOf(testEntities)

        // Act
        val result = dataSource.getFavoriteMovies().first()

        // Assert
        assertEquals(testEntities, result)
    }
}