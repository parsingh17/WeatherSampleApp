package com.parul.imdbapplication.data.repository

import com.parul.imdbapplication.data.apiService.WeatherAPI
import com.parul.imdbapplication.data.mappers.WeatherModelMapper
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.repositories.WeatherRepo

import io.reactivex.Single
import javax.inject.Inject


class WeatherApiServiceImpl @Inject constructor(
    private val apiService: WeatherAPI,
    private val WeatherModelMapper: dagger.Lazy<WeatherModelMapper>
): WeatherRepo {

    override fun getData(cityName: String): Single<WeatherConciseDetails>  = apiService.getData(cityName).map { WeatherModelMapper.get().toWeatherConciseDetails(it) }

    override fun getDataFromLatLng(latitude: String, longitude: String): Single<WeatherConciseDetails> {

        return apiService.getDataFromLatLng(latitude, longitude)
            .map { WeatherModelMapper.get().toWeatherConciseDetails(it) }
    }

}