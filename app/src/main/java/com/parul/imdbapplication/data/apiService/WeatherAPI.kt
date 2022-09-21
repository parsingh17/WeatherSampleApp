package com.parul.imdbapplication.data.apiService

import com.parul.imdbapplication.data.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=bingöl&APPID=04a42b96398abc8e4183798ed22f9485

    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=04a42b96398abc8e4183798ed22f9485
    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getDataFromLatLng(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): Single<WeatherModel>
}