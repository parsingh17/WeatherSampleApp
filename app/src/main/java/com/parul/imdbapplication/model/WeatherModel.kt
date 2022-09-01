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
)