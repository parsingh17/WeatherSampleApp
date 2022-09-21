package com.parul.imdbapplication.dagger

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.parul.imdbapplication.BuildConfig
import com.parul.imdbapplication.data.apiService.WeatherAPI
import com.parul.imdbapplication.data.repository.WeatherApiServiceImpl
import com.parul.imdbapplication.common.BASE_URL
import com.parul.imdbapplication.domain.repositories.WeatherRepo
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object ApiModule{

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache?): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20,TimeUnit.SECONDS)
        client.addInterceptor(interceptor)

        client.addInterceptor { chain ->
            val request: Request = chain.request().newBuilder().addHeader("APPID", BuildConfig.apiKey).build()
            chain.proceed(request)
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson?, okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WeatherAPI = retrofit.create(WeatherAPI::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: WeatherApiServiceImpl): WeatherRepo = apiHelper
}