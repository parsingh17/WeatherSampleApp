package com.parul.imdbapplication.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherConciseDetails(
    var weatherMain: String = "",
    var mainDataTemp: String = "",
    var weatherIconUrl: String = "",
    var weatherDescription: String = ""
) : Parcelable
