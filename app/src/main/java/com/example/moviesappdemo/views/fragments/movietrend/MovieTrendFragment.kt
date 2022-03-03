package com.example.moviesappdemo.views.fragments.movietrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesappdemo.databinding.FragmentMovieTrendBinding
import com.example.moviesappdemo.util.extensions.ViewPagerObject
import com.example.moviesappdemo.util.extensions.setUpViewPager
import com.example.moviesappdemo.views.fragments.movietrend.latestmovies.LatestMoviesFragment
import com.example.moviesappdemo.views.fragments.movietrend.trendingmovies.TrendingMoviesFragment
import com.example.moviesappdemo.views.fragments.movietrend.upcomingmovies.UpcomingMoviesFragment


class MovieTrendFragment : Fragment() {
    private var _binding: FragmentMovieTrendBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieTrendBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewer()
    }

    private fun setUpViewer() {
        val listOfPages = listOf(
            ViewPagerObject(LatestMoviesFragment(), "Latest"),
            ViewPagerObject(UpcomingMoviesFragment(), "Upcoming"),
            ViewPagerObject(TrendingMoviesFragment(), "Popular")
        )
        binding.viewPager.setUpViewPager(listOfPages, childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}