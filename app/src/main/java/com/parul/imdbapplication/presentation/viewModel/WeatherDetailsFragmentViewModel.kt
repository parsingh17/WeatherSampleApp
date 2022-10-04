package com.parul.imdbapplication.presentation.viewModel

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parul.imdbapplication.common.SingleLiveEvent
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.usecases.GetWeatherByCityUseCase
import com.parul.imdbapplication.domain.usecases.GetWeatherByLatLngUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherDetailsFragmentViewModel @Inject constructor(
    val app: Application,
    private val getWeatherByLatLngUseCase: GetWeatherByLatLngUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()
    var navigationCommand = SingleLiveEvent<Int>()

    private val weatherMutableData = MutableLiveData<WeatherConciseDetails>()
    val weatherDetailLiveData : LiveData<WeatherConciseDetails>
        get() = weatherMutableData


    fun handleArguments(arguments: Bundle?) {
        var lat = ""
        var lng = ""
        arguments?.getString("LAT")?.let {
            lat = it
            Log.d("WEATHER", "lat == $lat")
        }
        arguments?.getString("LNG")?.let {
            lng = it
            Log.d("WEATHER", "lng == $lng")
        }
        if (lat.isNotEmpty() && lng.isNotEmpty())
            getWeatherDataFromLatLng(lat, lng)
    }

    fun getWeatherDataByCity(cityName: String = "New York") {
        getWeatherByCityUseCase.getDataByCity(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherMutableData.value = it
                Log.d("WEATHER", "onSuccess: Success => ${it.mainDataTemp}")
                navigationCommand.value = UPDATE_WEATHER_DATA
            }, {
                Log.e("WEATHER",it.message ?: it.toString())
                navigationCommand.value = UPDATE_ERROR
            }).let { compositeDisposable.add(it) }

    }

    fun getWeatherDataFromLatLng(lat: String, lng: String) {
        getWeatherByLatLngUseCase.getDataFromLatLng(lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherMutableData.value = it
                Log.d("WEATHER", "onSuccess: Success => ${it.mainDataTemp}")
                navigationCommand.value = UPDATE_WEATHER_DATA
            }, {
                Log.e("WEATHER",it.message ?: it.toString())
                navigationCommand.value = UPDATE_ERROR
            }).let { compositeDisposable.add(it) }

    }

    fun onBackPressed() {
        navigationCommand.value = NAV_BACK
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val UPDATE_ERROR = 0
        const val UPDATE_WEATHER_DATA = 1
        const val NAV_BACK = 2
    }

}