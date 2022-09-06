package com.parul.imdbapplication.apiService

import com.parul.imdbapplication.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface WeatherAPIService {

    //http://api.openweathermap.org/data/2.5/weather?q=bingol&APPID=04a42b96398abc8e4183798ed22f9485

 /*   private val BASE_URL = "http://api.openweathermap.org/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)*/

    fun getData(cityName: String): Single<WeatherModel>

    fun getDataFromLatLng(latitude: String, longitude: String): Single<WeatherModel>

}