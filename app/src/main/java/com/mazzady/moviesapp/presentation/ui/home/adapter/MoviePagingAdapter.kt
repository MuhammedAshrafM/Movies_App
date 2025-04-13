package com.mazzady.moviesapp.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.mazzady.moviesapp.BuildConfig
import com.mazzady.moviesapp.R
import com.mazzady.moviesapp.databinding.ItemMovieGridBinding
import com.mazzady.moviesapp.databinding.ItemMovieListBinding
import com.mazzady.moviesapp.domain.model.Movie

class MoviePagingAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onFavoriteClick: (Int) -> Unit
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private const val VIEW_TYPE_GRID = 1
        private const val VIEW_TYPE_LIST = 2

        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridLayout) VIEW_TYPE_GRID else VIEW_TYPE_LIST
    }

    var isGridLayout: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGridLayout) {
            GridViewHolder(
                ItemMovieGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ListViewHolder(
                ItemMovieListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("", "onBindViewHolder: ${getItem(position)}")
        getItem(position)?.let { movie ->
            when (holder) {
                is GridViewHolder -> holder.bind(movie)
                is ListViewHolder -> holder.bind(movie)
            }
        }
    }

    inner class GridViewHolder(
        private val binding: ItemMovieGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Log.d("", "GridViewHolder: ${movie}")
            binding.apply {
                posterImage.load("${BuildConfig.IMAGE_URL}${movie.posterPath}") {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
                titleText.text = movie.title
                yearText.text = movie.releaseDate.take(4)
                favoriteButton.setImageResource(
                    if (movie.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
                favoriteButton.setOnClickListener {
                    onFavoriteClick(movie.id)
                }
                root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

    inner class ListViewHolder(
        private val binding: ItemMovieListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Log.d("", "ListViewHolder: ${movie}")
            binding.apply {
                posterImage.load("${BuildConfig.IMAGE_URL}${movie.posterPath}") {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
                titleText.text = movie.title
                yearText.text = movie.releaseDate.take(4)
                overviewText.text = movie.overview
                favoriteButton.setImageResource(
                    if (movie.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
                favoriteButton.setOnClickListener {
                    onFavoriteClick(movie.id)
                }
                root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }
}