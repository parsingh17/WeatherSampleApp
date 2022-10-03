package com.parul.imdbapplication.data.mappers

import com.parul.imdbapplication.common.IMAGE_RESOLUTION
import com.parul.imdbapplication.common.IMAGE_URL
import com.parul.imdbapplication.data.model.WeatherModel
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import javax.inject.Inject

/**
 * A mapper to map the WeatherModel from server to WeatherConciseDetails in a presentable form.
 */
class WeatherModelMapper @Inject constructor(){
    fun toWeatherConciseDetails(weatherModelServer: WeatherModel): WeatherConciseDetails {
        return WeatherConciseDetails(
            weatherMain = weatherModelServer.weather[0].main,
            mainDataTemp = weatherModelServer.main.temp.toString() + "°C",
            weatherIconUrl = IMAGE_URL + weatherModelServer.weather[0].icon + IMAGE_RESOLUTION,
            weatherDescription = weatherModelServer.weather[0].description
        )
    }
}