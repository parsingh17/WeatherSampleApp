package com.parul.imdbapplication.domain.model


data class WeatherConciseDetails(
    var weatherMain: String = "",
    var mainDataTemp: String = "",
    var weatherIconUrl: String = "",
    var weatherDescription: String = ""
)
