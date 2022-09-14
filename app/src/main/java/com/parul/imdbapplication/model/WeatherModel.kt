package com.parul.imdbapplication.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    val base: String,
    val main: MainData,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>
) {
    constructor(coord: Coord, main: MainData, weather: List<Weather>) : this(coord = coord,
            main = main,
            weather = weather,
            base = "test",
            cod = 2,
            dt = 1209121,
            id = 121,
            name = "Delhi",
            timezone = 1212,
            visibility = 1)
}