package com.mazzady.moviesapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazzady.moviesapp.R
import com.mazzady.moviesapp.databinding.FragmentHomeBinding
import com.mazzady.moviesapp.presentation.common.NetworkMonitor
import com.mazzady.moviesapp.presentation.ui.home.adapter.MoviePagingAdapter
import com.mazzady.moviesapp.presentation.ui.home.mvi.HomeIntent
import com.mazzady.moviesapp.presentation.ui.home.mvi.HomeViewModel
import com.mazzady.moviesapp.presentation.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private lateinit var movieAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeViewModel()
        observeNetworkStatus()
    }

    private fun setupRecyclerView() {
        movieAdapter = MoviePagingAdapter(
            onItemClick = { movie ->
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movie.id)
                )
            },
            onFavoriteClick = { movieId ->
                viewModel.processIntent(
                    HomeIntent.ToggleFavorite(movieId)
                )
            }
        )

        binding.recyclerView.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                pagingData?.let {
                    movieAdapter.submitData(pagingData)
                }
            }
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                errorText.isVisible = loadState.source.refresh is LoadState.Error
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading

                if (loadState.source.refresh is LoadState.Error) {
                    errorText.text = getString(R.string.error_loading_movies)
                }
            }
        }

    }

    private fun setupListeners() {
        binding.apply {

            retryButton.setOnClickListener {
                movieAdapter.retry()
            }

            toggleLayout.setOnClickListener {
                viewModel.processIntent(
                    HomeIntent.ToggleLayout
                )
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.isGridLayout.collect { isGrid ->

                binding.toggleLayout.setImageResource(
                    if (isGrid) R.drawable.ic_list
                    else R.drawable.ic_grid
                )
                movieAdapter.isGridLayout = isGrid

                binding.recyclerView.layoutManager = if (isGrid) {
                    GridLayoutManager(requireContext(), 2)
                } else {
                    LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun observeNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            networkMonitor.isOnline.collect { isOnline ->
                if (!isOnline) {
                    binding.container.showSnackbar(getString(R.string.offline_mode))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}