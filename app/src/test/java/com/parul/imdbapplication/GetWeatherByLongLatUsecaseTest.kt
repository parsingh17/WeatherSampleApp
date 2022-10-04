
package com.parul.imdbapplication

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.parul.imdbapplication.common.SingleLiveEvent
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.repositories.WeatherRepo
import com.parul.imdbapplication.domain.usecases.GetWeatherByLatLngUseCase
import io.reactivex.Observer
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetWeatherByLongLatUsecaseTest {
    // A JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously.
    // You can use this rule for your host side tests that use Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var weatherRepository: WeatherRepo


    lateinit var getWeatherByLatLngUseCase: GetWeatherByLatLngUseCase

    @Before
    fun setUp() {
        // initialize the usecase with a mocked github api
        getWeatherByLatLngUseCase = GetWeatherByLatLngUseCase(weatherRepository)
    }

    @Test
    fun `test getWeatherByLatLngUseCase with success`() {

        val lat = "2222222221212871298712987"
        val long = "21"
        val mockWeatherDate = WeatherConciseDetails(
            mainDataTemp = "24.56°C",
            weatherMain = "Cloud",
            weatherDescription = "scattered clouds",
            weatherIconUrl = "https://openweathermap.org/img/wn/03n@2x.png"
        )

        `when`(getWeatherByLatLngUseCase.getDataFromLatLng(lat, long))
            .thenReturn(Single.just(mockWeatherDate))

        getWeatherByLatLngUseCase.getDataFromLatLng(lat, long)
            .test()
            .assertComplete()

    }

}

