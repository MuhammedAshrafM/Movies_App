package com.mazzady.moviesapp.domain

import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.repository.MovieRepository
import com.mazzady.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoritesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase = GetFavoriteMoviesUseCase(repository)

    @Test
    fun `invoke should return flow of favorite movies`() = runTest {
        // Arrange
        val testMovies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "", posterPath = "", releaseDate = "", isFavorite = true)
        )
        coEvery { repository.getFavoriteMovies() } returns flowOf(testMovies)

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(testMovies, result)
    }
}