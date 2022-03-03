package com.example.moviesappdemo.views.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.moviesappdemo.R
import com.example.moviesappdemo.databinding.FragmentSettingsBinding
import com.example.moviesappdemo.util.extensions.hide
import com.example.moviesappdemo.util.extensions.show
import com.example.moviesappdemo.util.observeChange

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        setObservers()
        binding.container.setOnClickListener {
            viewModel.clearAppData()
        }
    }

    private fun setObservers() {
        viewModel.showLoading.observeChange(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.clearAppListener.observeChange(viewLifecycleOwner) {
            Toast.makeText(context, getString(R.string.clear_app_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(toShow: Boolean) {
        if (toShow) binding.progress.show() else binding.progress.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}