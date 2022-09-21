package com.parul.imdbapplication.domain.usecases

import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.repositories.WeatherRepo
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor (val weatherRepository: WeatherRepo) {

    operator fun invoke(city: String): Single<WeatherConciseDetails> {
        return weatherRepository.getData(city)
    }
}