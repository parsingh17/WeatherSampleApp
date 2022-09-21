package com.parul.imdbapplication.data.model

import com.google.gson.annotations.SerializedName
data class MainData(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
) {
    constructor(temp: Double) : this(
         feelsLike= 12.1212,
    grndLevel=12,
     humidity=12,
     pressure=12,
    seaLevel=123,
    temp=temp,
    tempMax=30.12,
    tempMin=20.12
    )
}
