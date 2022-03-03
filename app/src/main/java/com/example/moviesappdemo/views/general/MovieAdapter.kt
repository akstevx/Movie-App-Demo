package com.example.moviesappdemo.views.general

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappdemo.BuildConfig
import com.example.moviesappdemo.R
import com.example.moviesappdemo.databinding.MovieItemLayoutBinding
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.util.extensions.*
import com.example.moviesappdemo.views.fragments.movietrend.MovieTrendFragmentDirections

class MoviesAdapter() :
    PagedListAdapter<MovieModel, MoviesAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            MovieItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), context
        )
    }


    class ViewHolder(
        private val binding: MovieItemLayoutBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieModel?) {
            binding.apply {
                txtMovieTitle.text = item?.title?.capitalizeWords()
                txtMoviePopularity.text = "Popularity: ${item?.popularity}"
                txtMovieDesc.text = item?.overview
                ivMoviePoster.loadImage(
                    fullImageUrl = BuildConfig.IMAGE_URL + "/" + item?.poster_path,
                    R.drawable.dummy_poster, context
                )

                item?.let { movie ->
                    container.setOnClickListener {
                        val direction =
                            MovieTrendFragmentDirections.navigateToMovieDetailFragment(movie = movie)
                        it.findNavController().navigate(direction)
                    }
                }

                btnLike.setOnClickListener {
                    if (ivLike.visibility == View.VISIBLE) {
                        ivLike.chill()
                        ivUnLike.show()
                    } else {
                        ivLike.show()
                        ivUnLike.chill()
                    }
                }
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}
