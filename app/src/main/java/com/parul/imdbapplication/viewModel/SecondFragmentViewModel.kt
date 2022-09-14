package com.parul.imdbapplication.viewModel

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.parul.imdbapplication.model.WeatherModel
import com.parul.imdbapplication.repository.WeatherRepository
import com.parul.imdbapplication.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SecondFragmentViewModel @Inject constructor(
    val app: Application,
    val repository: WeatherRepository
) : AndroidViewModel(app){

    private val compositeDisposable = CompositeDisposable()
    var navigationCommand = SingleLiveEvent<Int>()
    lateinit var weather_data: WeatherModel

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
        if (!lat.isNullOrEmpty() && !lng.isNullOrEmpty())
            getWeatherDataFromLatLng(lat, lng)
    }

    fun getWeatherData(cityName: String = "New York") {
        repository.getWeatherData(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weather_data = it
                Log.d("WEATHER", "onSuccess: Success => ${it.main.temp}")
                navigationCommand.value = UPDATE_WEATHER_DATA
            }, {
                Log.e("WEATHER",it.message ?: it.toString())
                navigationCommand.value = UPDATE_ERROR
            }).let { compositeDisposable.add(it) }

    }

    fun getWeatherDataFromLatLng(lat: String, lng: String) {
        repository.getWeatherDataFromLatLng(lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weather_data = it
                Log.d("WEATHER", "onSuccess: Success => ${it.main.temp}")
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