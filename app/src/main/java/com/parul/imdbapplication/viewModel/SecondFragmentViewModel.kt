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
    private var cityName: String = "delhi"
    val weather_data = MutableLiveData<WeatherModel>()
    var degreesTextViewField = ObservableField<String>()


    fun handleArguments(arguments: Bundle?) {
        arguments?.getString("CITY NAME")?.let {
            cityName = it
            Log.d("WEATHER", "cityName == $cityName")
        }
        getWeatherData()
    }

    private fun getWeatherData() {
        repository.getWeatherData(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            weather_data.value = it
            Log.d("WEATHER", "onSuccess: Success => ${it.main.temp}")
            degreesTextViewField.set(it.main.temp.toString())
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