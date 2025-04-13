package com.mazzady.moviesapp.presentation.favorites


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import com.mazzady.moviesapp.domain.usecase.ToggleFavoriteUseCase
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesIntent
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesState
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getFavoritesUseCase: GetFavoriteMoviesUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = FavoritesViewModel(
            getFavoritesUseCase,
            toggleFavoriteUseCase,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFavorites should emit Success state with movies`() = runTest {
        // Arrange
        val testMovies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "", posterPath = "", releaseDate = "", isFavorite = true)
        )
        coEvery { getFavoritesUseCase() } returns flowOf(testMovies)

        // Act
        viewModel.processIntent(FavoritesIntent.LoadFavorites)

        // Assert
        assert(viewModel.state.value is FavoritesState.Success)
    }

    // Additional test cases...
}