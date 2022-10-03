package com.parul.imdbapplication.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.parul.imdbapplication.databinding.FragmentWeatherDetailsBinding
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel.Companion.NAV_BACK
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel.Companion.UPDATE_ERROR
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel.Companion.UPDATE_WEATHER_DATA
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherDetailsFragment : BaseFragment(), WeatherDetailsFragmentEventListener {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var viewModel: WeatherDetailsFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, mViewModelFactory)[WeatherDetailsFragmentViewModel::class.java]
        subscribeToNavigationChanges()
        handleArguments()
        setupBackPress()
        setupClickListener()

        return with(binding) { root }
    }

    private fun setupClickListener() {
        binding.buttonPrevious.setOnClickListener{
            viewModel.onBackPressed()
        }
    }

    private fun handleArguments() {
        viewModel.handleArguments(arguments)
    }

    private fun subscribeToNavigationChanges() =
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer {
            when (it) {
                UPDATE_ERROR -> {
                    Toast.makeText(context,"error getting weather updates!!!", Toast.LENGTH_SHORT).show()
                }
                UPDATE_WEATHER_DATA -> {
                    updateWeatherDetails()
                }
                NAV_BACK -> {
                    navigateUp()
                }

            }
        })

    private fun updateWeatherDetails() {

        viewModel.weatherDetailLiveData.observe(viewLifecycleOwner) { data ->
            binding.tvMain.text = data.weatherMain
            binding.tvDesc.text = data.weatherDescription
            binding.tvDegrees.text = data.mainDataTemp
            Glide.with(this)
                .load(data.weatherIconUrl)
                .into(binding.imageview)
        }
    }

    private fun setupBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onBackPressed()
                }
            })
    }


    override fun onBackButtonClicked() {
        viewModel.onBackPressed()
    }
}

interface WeatherDetailsFragmentEventListener {
    fun onBackButtonClicked()
}


