package com.parul.imdbapplication.presentation.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.parul.imdbapplication.common.SingleLiveEvent
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrentLocationFragmentViewModel @Inject constructor(
    val app: Application
) : ViewModel(){

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var myCurrentLatLngLiveData: LatLng? = null
    var navigationCommand = SingleLiveEvent<Int>()

    private var address = MutableLiveData<String>()
    val addressLiveData : LiveData<String>
        get() = address

    lateinit var geocoder: Geocoder

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)
        fusedLocationProviderClient.flushLocations()

        val locationRequest = LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(2000)
            fastestInterval = TimeUnit.SECONDS.toMillis(1200)
            maxWaitTime = TimeUnit.MINUTES.toMillis(1000)
            numUpdates = 2
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                myCurrentLatLngLiveData = locationResult.lastLocation?.let {
                    LatLng(it.latitude, it.longitude)
                }
                updateCurrentAddress()
                navigationCommand.value = EVENT_FETCHED_LOCATION
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun updateCurrentAddress() {
        myCurrentLatLngLiveData?.let {
            val addresses = Geocoder(app, Locale.getDefault()).getFromLocation(
                it.latitude,
                it.longitude, 1)
            address.value = addresses?.get(0)?.getAddressLine(0)
        }

        Log.d("WEATHER","address = ${address.value}")
    }

    fun onNextButtonClicked() {
        navigationCommand.value = NAV_NEXT
    }

    fun onBackPressed() {
        navigationCommand.value = NAV_BACK
    }

    companion object {
        const val NAV_NEXT = 0
        const val NAV_BACK = 2
        const val EVENT_FETCHED_LOCATION = 3
    }

}