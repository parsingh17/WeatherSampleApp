package com.parul.imdbapplication.domain.repositories

import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import io.reactivex.Single

interface WeatherRepo {

    fun getData(cityName: String): Single<WeatherConciseDetails>
    fun getDataFromLatLng(latitude: String, longitude: String): Single<WeatherConciseDetails>
}