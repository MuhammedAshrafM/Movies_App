package com.mazzady.moviesapp.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil3.load
import coil3.request.placeholder
import com.google.android.material.chip.Chip
import com.mazzady.moviesapp.BuildConfig
import com.mazzady.moviesapp.R
import com.mazzady.moviesapp.databinding.FragmentMovieDetailsBinding
import com.mazzady.moviesapp.domain.model.MovieDetails
import com.mazzady.moviesapp.presentation.ui.details.mvi.DetailsIntent
import com.mazzady.moviesapp.presentation.ui.details.mvi.DetailsState
import com.mazzady.moviesapp.presentation.ui.details.mvi.DetailsViewModel
import com.mazzady.moviesapp.presentation.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeState()
    }

    private fun setupViews() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            favoriteButton.setOnClickListener {
                viewModel.processIntent(
                    DetailsIntent.ToggleFavorite
                )
            }

            retryButton.setOnClickListener {
                viewModel.retry()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is DetailsState.Loading -> showLoading()
                    is DetailsState.Success -> showMovieDetails(state.movieDetails)
                    is DetailsState.Error -> showError(state.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            errorGroup.isVisible = false
        }
    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        binding.apply {
            progressBar.isVisible = false
            errorGroup.isVisible = false

            backdropImage.load("${BuildConfig.IMAGE_URL}${movieDetails.backdropPath}") {
                placeholder(R.drawable.ic_movie_placeholder)
            }

            posterImage.load("${BuildConfig.IMAGE_URL}${movieDetails.posterPath}") {
                placeholder(R.drawable.ic_movie_placeholder)
            }

            titleText.text = movieDetails.title
            releaseDateText.text = movieDetails.releaseDate
            ratingText.text = getString(R.string.rating_format, movieDetails.voteAverage)
            runtimeText.text = getString(R.string.runtime_format, movieDetails.runtime)
            overviewText.text = movieDetails.overview

            favoriteButton.setImageResource(
                if (movieDetails.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )

            genresChipGroup.removeAllViews()
            movieDetails.genres.forEach { genre ->
                val chip = Chip(requireContext()).apply {
                    text = genre
                    isClickable = false
                }
                genresChipGroup.addView(chip)
            }
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.isVisible = false
            errorGroup.isVisible = true
            errorText.text = message
            root.showSnackbar(message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}