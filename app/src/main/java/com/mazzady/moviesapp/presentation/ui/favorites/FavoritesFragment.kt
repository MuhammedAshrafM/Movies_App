package com.mazzady.moviesapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazzady.moviesapp.databinding.FragmentFavoritesBinding
import com.mazzady.moviesapp.domain.model.Movie
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesIntent
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesState
import com.mazzady.moviesapp.presentation.ui.favorites.mvi.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is FavoritesState.Loading -> showLoading()
                    is FavoritesState.Success -> showFavorites(state.movies)
                    is FavoritesState.Empty -> showEmptyState()
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            recyclerView.isVisible = false
            emptyState.isVisible = false
            errorState.isVisible = false
        }
    }

    private fun showFavorites(movies: List<Movie>) {
        binding.apply {
            progressBar.isVisible = false
            recyclerView.isVisible = true
            emptyState.isVisible = false
            errorState.isVisible = false
        }
        adapter.submitList(movies)
    }

    private fun showEmptyState() {
        binding.apply {
            progressBar.isVisible = false
            recyclerView.isVisible = false
            emptyState.isVisible = true
            errorState.isVisible = false
        }
    }

    private fun setupListeners() {
        binding.retryButton.setOnClickListener {
            viewModel.processIntent(FavoritesIntent.LoadFavorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}