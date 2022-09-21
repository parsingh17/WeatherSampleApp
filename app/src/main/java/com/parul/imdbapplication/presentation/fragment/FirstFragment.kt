package com.parul.imdbapplication.presentation.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.parul.imdbapplication.R
import com.parul.imdbapplication.databinding.FragmentFirstBinding
import androidx.databinding.DataBindingUtil
import com.parul.imdbapplication.presentation.viewModel.FirstFragmentViewModel
import androidx.lifecycle.Observer
import com.parul.imdbapplication.common.LOCATION_PERMISSION_REQUEST
import com.parul.imdbapplication.common.LOCATION_PERMISSION_STRING
import com.parul.imdbapplication.common.PermissionsUtil
import com.parul.imdbapplication.presentation.viewModel.FirstFragmentViewModel.Companion.EVENT_FETCHED_LOCATION
import com.parul.imdbapplication.presentation.viewModel.FirstFragmentViewModel.Companion.NAV_BACK
import com.parul.imdbapplication.presentation.viewModel.FirstFragmentViewModel.Companion.NAV_NEXT
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(), FirstFragmentEventListener {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: FirstFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        viewModel = ViewModelProvider(this, mViewModelFactory).get(FirstFragmentViewModel::class.java)
        subscribeToNavigationChanges()
        subscribeLocationUpdate()
        checkLocationPermission()
        setupBackPress()
        return with(binding) {
            firstFragmentEventListener = this@FirstFragment
            vm = viewModel
            root
        }
    }

    private fun subscribeToNavigationChanges() =
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer {
            when (it) {
                EVENT_FETCHED_LOCATION -> {
                    Log.d("WEATHER","EVENT_FETCHED_LOCATION")
                }
                NAV_NEXT -> {
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        Bundle().apply {
                            putString("LAT", viewModel.myCurrentLatLngLiveData?.latitude.toString())
                            putString("LNG", viewModel.myCurrentLatLngLiveData?.longitude.toString())
                        })

                }
                NAV_BACK -> {
                    navigateUp()
                }

            }
        })

    private fun checkLocationPermission() {
        if (PermissionsUtil.isLocationOn(requireContext())) {
            if (!PermissionsUtil.checkPermissionAccess(this, LOCATION_PERMISSION_STRING)) {
                PermissionsUtil.requestPermissionAccess(this, LOCATION_PERMISSION_STRING, LOCATION_PERMISSION_REQUEST)
            }
            else {
                viewModel.requestLocationUpdate()
            }
        }
        else {
            //showLocationOffPopup(activity as FragmentActivity)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.requestLocationUpdate()
                } else {
                    Toast.makeText(activity, "Please provide Location permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun subscribeLocationUpdate() {

        viewModel.address.observe(viewLifecycleOwner, Observer {
            Log.d("WEATHER","subscribeLocationUpdate")
            tv_address.text = viewModel.address.value
        })
    }

    /*
    * Handle devices back button press
    */
    private fun setupBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onBackPressed()
                }
            })
    }

    override fun onNextButtonClicked() {
        viewModel.onNextButtonClicked()
    }
}

interface FirstFragmentEventListener {
    fun onNextButtonClicked()
}


