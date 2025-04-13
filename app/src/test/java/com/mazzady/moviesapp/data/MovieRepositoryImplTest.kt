package com.mazzady.moviesapp.data

import com.mazzady.moviesapp.data.local.datasource.MovieLocalDataSource
import com.mazzady.moviesapp.data.local.entity.MovieEntity
import com.mazzady.moviesapp.data.remote.datasource.MovieRemoteDataSource
import com.mazzady.moviesapp.data.repository.MovieRepositoryImpl
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private val localDataSource: MovieLocalDataSource = mockk()
    private val remoteDataSource: MovieRemoteDataSource = mockk()
    private val networkMonitor: NetworkMonitor = mockk()
    private val repository = MovieRepositoryImpl(remoteDataSource, localDataSource, networkMonitor)

    @Test
    fun `getFavoriteMovies should return mapped movies`() = runTest {
        // Arrange
        val testEntities = listOf(
            MovieEntity(id = 1, title = "Movie 1", overview = "", posterPath = "", releaseDate = "", isFavorite = true)
        )
        every { localDataSource.getFavoriteMovies() } returns flowOf(testEntities)

        // Act
        val result = repository.getFavoriteMovies().first()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0].isFavorite)
    }
}