package com.parul.imdbapplication.presentation.viewModel

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableParcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parul.imdbapplication.common.SingleLiveEvent
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.usecases.GetWeatherByCityUseCase
import com.parul.imdbapplication.domain.usecases.GetWeatherByLatLngUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SecondFragmentViewModel @Inject constructor(
    val app: Application,
    val getWeatherByLatLngUseCase: GetWeatherByLatLngUseCase,
    val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : AndroidViewModel(app){

    private val compositeDisposable = CompositeDisposable()
    var navigationCommand = SingleLiveEvent<Int>()
    //lateinit var weatherData: ObservableField<WeatherConciseDetails>

/*    private val _weatherData = MutableStateFlow<WeatherConciseDetails>(WeatherConciseDetails())
    val mData: StateFlow<WeatherConciseDetails> = _weatherData*/
    private val weatherMutableData = MutableLiveData<WeatherConciseDetails>()
    val weatherDetailLiveData : LiveData<WeatherConciseDetails>
        get() = weatherMutableData

   /* val weatherData : ObservableParcelable<WeatherConciseDetails>
        get() = weatherMutableData.value*/


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

    fun getWeatherDataByCity(cityName: String = "New York") {
        getWeatherByCityUseCase.invoke(cityName)
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

        /*repository.getWeatherData(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weather_data = it
                Log.d("WEATHER", "onSuccess: Success => ${it.main.temp}")
                navigationCommand.value = UPDATE_WEATHER_DATA
            }, {
                Log.e("WEATHER",it.message ?: it.toString())
                navigationCommand.value = UPDATE_ERROR
            }).let { compositeDisposable.add(it) }*/

    }

    fun getWeatherDataFromLatLng(lat: String, lng: String) {
        getWeatherByLatLngUseCase.invoke(lat, lng)
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