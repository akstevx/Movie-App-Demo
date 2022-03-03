package com.example.moviesappdemo.views.fragments.movietrend.upcomingmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappdemo.databinding.FragmentUpcomingMoviesBinding
import com.example.moviesappdemo.datasource.network.api.NetworkState
import com.example.moviesappdemo.util.ConnectivityUtil
import com.example.moviesappdemo.util.extensions.hide
import com.example.moviesappdemo.util.extensions.show
import com.example.moviesappdemo.util.observeChange
import com.example.moviesappdemo.views.general.MoviesAdapter


class UpcomingMoviesFragment : Fragment() {
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingMoviesViewModel by activityViewModels()
    private var isConnected: Boolean = true
    private val adapter = MoviesAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetStatus()

        updateUI(adapter)
        setUpRefresher(adapter)
    }

    private fun checkInternetStatus() {
        isConnected = ConnectivityUtil.isConnected(context)
        if (!isConnected)
            Toast.makeText(
                context?.applicationContext,
                "No internet connection!",
                Toast.LENGTH_SHORT
            ).show()

    }

    private fun updateUI(adapter: MoviesAdapter) {
        setUpRecycler(adapter)

        val data = viewModel.fetchUpcomingMovies(isConnected)

        data?.networkState?.observeChange(viewLifecycleOwner) {
            when (it) {
                is NetworkState.ERROR -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    binding.progress.hide()
                }
                NetworkState.HAS_LOADED -> {
                    binding.progress.hide()
                }
                NetworkState.IS_LOADING -> {
                    binding.progress.show()
                }
            }
        }

        data?.pagedList?.observeChange(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    private fun setUpRecycler(adapter: MoviesAdapter) {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recycler.adapter = adapter
    }

    private fun setUpRefresher(adapter: MoviesAdapter) {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
            updateUI(adapter)
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        checkInternetStatus()

        updateUI(adapter)
        setUpRefresher(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}