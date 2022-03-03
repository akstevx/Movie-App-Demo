package com.example.moviesappdemo.views.fragments.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesappdemo.BuildConfig
import com.example.moviesappdemo.R
import com.example.moviesappdemo.databinding.FragmentMovieDetailBinding
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.util.extensions.loadImage

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI(args.movie)
    }

    private fun updateUI(movie: MovieModel) {
        (activity as AppCompatActivity?)?.supportActionBar?.title = movie.title

        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageView4.loadImage(
            fullImageUrl = BuildConfig.IMAGE_URL + "/" + movie.poster_path,
            R.drawable.dummy_poster, requireContext()
        )

        binding.txtMovieTitle.text = movie.title

        binding.txtMovieDesc.text = movie.overview

        binding.txtMoviePopularity.text = "Popularity: ${movie.popularity}"

        binding.txtMovieVoteAverage.text = "Vote average : ${movie.vote_average}"

        binding.txtMovieVoteCount.text = "Vote count: ${movie.vote_count}"

        binding.txtReleaseDate.text = "Release date: ${movie.release_date}"

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}