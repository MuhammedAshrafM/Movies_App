package com.mazzady.moviesapp.presentation.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.placeholder
import com.mazzady.moviesapp.BuildConfig
import com.mazzady.moviesapp.R
import com.mazzady.moviesapp.databinding.ItemFavoriteBinding
import com.mazzady.moviesapp.domain.model.Movie

class FavoritesAdapter(
) : ListAdapter<Movie, FavoritesAdapter.FavoritesViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoritesViewHolder(
        private val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                posterImage.load("${BuildConfig.IMAGE_URL}${movie.posterPath}") {
                    placeholder(R.drawable.ic_movie_placeholder)
                }

                titleText.text = movie.title
                releaseDate.text = movie.releaseDate.take(4)

                favoriteButton.setImageResource(
                    if (movie.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )

            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}