package com.parul.imdbapplication.repository

import android.app.Application
import com.parul.imdbapplication.apiService.WeatherAPIService
import com.parul.imdbapplication.model.WeatherModel
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    val app: Application,
    private val weatherServiceProvider: WeatherAPIService
) {

    fun getWeatherData(cityName: String): Single<WeatherModel> = weatherServiceProvider.getData(cityName)

    fun getWeatherDataFromLatLng(lat: String, lng: String): Single<WeatherModel> = weatherServiceProvider.getDataFromLatLng(lat, lng)

}