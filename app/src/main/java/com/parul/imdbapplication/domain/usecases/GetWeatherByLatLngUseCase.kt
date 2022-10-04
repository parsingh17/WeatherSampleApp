package com.parul.imdbapplication.domain.usecases

import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.repositories.WeatherRepo
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherByLatLngUseCase @Inject constructor (private val weatherRepository: WeatherRepo) {

    fun getDataFromLatLng(latitude: String, longitude: String): Single<WeatherConciseDetails> {
            return weatherRepository.getDataFromLatLng(latitude, longitude)
    }
}