
package com.parul.imdbapplication

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.parul.imdbapplication.data.model.WeatherModel
import com.parul.imdbapplication.common.SingleLiveEvent
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import com.parul.imdbapplication.domain.usecases.GetWeatherByCityUseCase
import com.parul.imdbapplication.domain.usecases.GetWeatherByLatLngUseCase
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel
import io.reactivex.Observer
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class WeatherDetailsFragmentViewModelTest {
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
    lateinit var getWeatherByCityUseCase: GetWeatherByCityUseCase

    @Mock
    lateinit var getWeatherByLatLngUseCase: GetWeatherByLatLngUseCase

    @Mock
    lateinit var observer: Observer<SingleLiveEvent<Int>>

    lateinit var weatherDetailsFragmentViewModel: WeatherDetailsFragmentViewModel

    private val application = Mockito.mock(Application::class.java)

    @Before
    fun setUp() {
        // initialize the ViewModel with a mocked github api
        weatherDetailsFragmentViewModel = WeatherDetailsFragmentViewModel(application, getWeatherByLatLngUseCase, getWeatherByCityUseCase)
    }

    @Test
    fun `test getWeatherDataFromLatLng with success`() {
        // mock data
        val lat = "1.22"
        val long = "2.22"

        val mockWeatherDate = WeatherConciseDetails(
            mainDataTemp = "24.56°C",
            weatherMain = "Cloud",
            weatherDescription = "scattered clouds",
            weatherIconUrl = "https://openweathermap.org/img/wn/03n@2x.png"
        )

        // make the github api to return mock data
        Mockito.`when`(getWeatherByLatLngUseCase.getDataFromLatLng(lat, long))
            .thenReturn(Single.just(mockWeatherDate))

        // observe on the MutableLiveData with an observer
        weatherDetailsFragmentViewModel.navigationCommand.observeForever(observer)
        weatherDetailsFragmentViewModel.getWeatherDataFromLatLng(lat, long)

        assertEquals(WeatherDetailsFragmentViewModel.UPDATE_WEATHER_DATA, weatherDetailsFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherDataFromLatLng with failure`() {
        // mock data
        val lat = "2222222221212871298712987"
        val long = "-1"
        val mockWeatherDate = WeatherConciseDetails(
            mainDataTemp = "24.56°C",
            weatherMain = "Cloud",
            weatherDescription = "scattered clouds",
            weatherIconUrl = "https://openweathermap.org/img/wn/03n@2x.png"
        )

        var weatherMock = Mockito.mock(WeatherModel::class.java)

        // make the github api to return mock data
        Mockito.`when`(getWeatherByLatLngUseCase.getDataFromLatLng(lat, long))
            .thenReturn(Single.error(IOException()))

        // observe on the MutableLiveData with an observer
        weatherDetailsFragmentViewModel.navigationCommand.observeForever(observer)
        weatherDetailsFragmentViewModel.getWeatherDataFromLatLng(lat, long)

        assertEquals(WeatherDetailsFragmentViewModel.UPDATE_ERROR, weatherDetailsFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherData with success`() {
        // mock data
        val cityName = "Bengaluru"

        val mockWeatherDate = WeatherConciseDetails(
            mainDataTemp = "24.56°C",
            weatherMain = "Cloud",
            weatherDescription = "scattered clouds",
            weatherIconUrl = "https://openweathermap.org/img/wn/03n@2x.png"
        )

        // make the github api to return mock data
        Mockito.`when`(getWeatherByCityUseCase.getDataByCity(cityName))
            .thenReturn(Single.just(mockWeatherDate))

        // observe on the MutableLiveData with an observer
        weatherDetailsFragmentViewModel.navigationCommand.observeForever(observer)
        weatherDetailsFragmentViewModel.getWeatherDataByCity(cityName)

        assertEquals(WeatherDetailsFragmentViewModel.UPDATE_WEATHER_DATA, weatherDetailsFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherData with failure`() {

        // make the github api to return mock data
        Mockito.`when`(getWeatherByCityUseCase.getDataByCity(""))
            .thenReturn(Single.error(IOException()))

        // observe on the MutableLiveData with an observer
        weatherDetailsFragmentViewModel.navigationCommand.observeForever(observer)
        weatherDetailsFragmentViewModel.getWeatherDataByCity("")

        assertEquals(WeatherDetailsFragmentViewModel.UPDATE_ERROR, weatherDetailsFragmentViewModel.navigationCommand.value)
    }


    @Test
    fun `test onBackPressed`() {
        weatherDetailsFragmentViewModel.onBackPressed()
        assertEquals(WeatherDetailsFragmentViewModel.NAV_BACK, weatherDetailsFragmentViewModel.navigationCommand.value)
    }

}

private fun <T> SingleLiveEvent<T>.observeForever(observer: Observer<SingleLiveEvent<T>>) {

}
