package com.parul.imdbapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.parul.imdbapplication.R
import com.parul.imdbapplication.databinding.FragmentFirstBinding
import androidx.databinding.DataBindingUtil
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.parul.imdbapplication.databinding.FragmentSecondBinding
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel.Companion.NAV_BACK
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel.Companion.UPDATE_ERROR
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel.Companion.UPDATE_WEATHER_DATA
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SecondFragment : BaseFragment(), SecondFragmentEventListener {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var viewModel: SecondFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        viewModel = ViewModelProvider(this, mViewModelFactory).get(SecondFragmentViewModel::class.java)
        subscribeToNavigationChanges()
        handleArguments()
        setupBackPress()
        return with(binding) {
            secondFragmentEventListener = this@SecondFragment
            vm = viewModel
            root
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

        tv_main.text = viewModel.weather_data.weather[0].main
        tv_desc.text = viewModel.weather_data.weather[0].description
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/" + viewModel.weather_data.weather.get(0).icon + "@2x.png")
            .into(imageview)

        tv_degrees.text = viewModel.weather_data.main.temp.toString() + "°C"

        /*tv_humidity.text = data.main.humidity.toString() + "%"
        tv_wind_speed.text = data.wind.speed.toString()
        tv_lat.text = data.coord.lat.toString()
        tv_lon.text = data.coord.lon.toString()*/
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

interface SecondFragmentEventListener {
    fun onBackButtonClicked()
}


