package com.parul.imdbapplication.apiService

import com.parul.imdbapplication.model.WeatherModel

import io.reactivex.Single
import javax.inject.Inject


class WeatherApiServiceImpl @Inject constructor(
    private val apiService: WeatherAPI
): WeatherAPIService{
    override fun getData(cityName: String): Single<WeatherModel>  = apiService.getData(cityName)
}