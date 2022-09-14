package com.parul.imdbapplication.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.parul.imdbapplication.repository.WeatherRepository
import com.parul.imdbapplication.utils.SingleLiveEvent
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirstFragmentViewModel @Inject constructor(
    val app: Application,
    val repository: WeatherRepository
) : AndroidViewModel(app){

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var myCurrentLatLngLiveData: LatLng? = null
    var navigationCommand = SingleLiveEvent<Int>()
    var address = MutableLiveData<String>()
    lateinit var geocoder: Geocoder

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)
        fusedLocationProviderClient.flushLocations()

        val locationRequest = LocationRequest().apply {
 /*           interval = TimeUnit.SECONDS.toMillis(2000)
            fastestInterval = TimeUnit.SECONDS.toMillis(1200)*/
            maxWaitTime = TimeUnit.MINUTES.toMillis(1000)
            numUpdates = 1
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                myCurrentLatLngLiveData = locationResult?.lastLocation?.let { LatLng(it.latitude, it.longitude) }
                updateCurrentAddress()
                navigationCommand.value = EVENT_FETCHED_LOCATION
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun updateCurrentAddress() {
        //geocoder = Geocoder(app, Locale.getDefault())
        if (myCurrentLatLngLiveData != null) {
            val addresses = Geocoder(app, Locale.getDefault()).getFromLocation(
                myCurrentLatLngLiveData!!.latitude,
                myCurrentLatLngLiveData!!.longitude, 1)
            address.value = addresses[0].getAddressLine(0)
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