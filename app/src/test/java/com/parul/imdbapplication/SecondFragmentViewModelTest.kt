package com.parul.imdbapplication

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.parul.imdbapplication.model.Coord
import com.parul.imdbapplication.model.MainData
import com.parul.imdbapplication.model.Weather
import com.parul.imdbapplication.model.WeatherModel
import com.parul.imdbapplication.repository.WeatherRepository
import com.parul.imdbapplication.utils.SingleLiveEvent
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel
import io.reactivex.Observable
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
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class SecondFragmentViewModelTest {
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
    lateinit var weatherRepo: WeatherRepository

    @Mock
    lateinit var observer: Observer<SingleLiveEvent<Int>>

    lateinit var secondFragmentViewModel: SecondFragmentViewModel

    private val application = Mockito.mock(Application::class.java)

    @Before
    fun setUp() {
        // initialize the ViewModed with a mocked github api
        secondFragmentViewModel = SecondFragmentViewModel(application, weatherRepo)
    }

    @Test
    fun `test getWeatherDataFromLatLng with success`() {
        // mock data
        val lat = "1.22"
        val long = "2.22"
        var weather = Weather(id = 804, main = "clouds", description = "broken clouds", icon = "04n")
        var weatherList = mutableListOf<Weather>()
        weatherList.add(weather)
        val mockWeatherDate: WeatherModel = WeatherModel(
            coord = Coord(lat.toDouble(), long.toDouble()),
            main = MainData(temp = 12.75),
            weather = weatherList
        )

        // make the github api to return mock data
        Mockito.`when`(weatherRepo.getWeatherDataFromLatLng(lat, long))
            .thenReturn(Single.just(mockWeatherDate))

        // observe on the MutableLiveData with an observer
        secondFragmentViewModel.navigationCommand.observeForever(observer)
        secondFragmentViewModel.getWeatherDataFromLatLng(lat, long)

        assertEquals(SecondFragmentViewModel.UPDATE_WEATHER_DATA, secondFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherDataFromLatLng with failure`() {
        // mock data
        val lat = "2222222221212871298712987"
        val long = "-1"
        var weather = Weather(id = 804, main = "clouds", description = "broken clouds", icon = "04n")
        var weatherList = mutableListOf<Weather>()
        weatherList.add(weather)
        val mockWeatherDate: WeatherModel = WeatherModel(
            coord = Coord(lat.toDouble(), long.toDouble()),
            main = MainData(temp = 12.75),
            weather = weatherList
        )

        var weatherMock = Mockito.mock(WeatherModel::class.java)

        // make the github api to return mock data
        Mockito.`when`(weatherRepo.getWeatherDataFromLatLng(lat, long))
            .thenReturn(Single.error(IOException()))

        // observe on the MutableLiveData with an observer
        secondFragmentViewModel.navigationCommand.observeForever(observer)
        secondFragmentViewModel.getWeatherDataFromLatLng(lat, long)

        assertEquals(SecondFragmentViewModel.UPDATE_ERROR, secondFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherData with success`() {
        // mock data
        val cityName = "Bengaluru"
        var weather = Weather(id = 804, main = "clouds", description = "broken clouds", icon = "04n")
        var weatherList = mutableListOf<Weather>()
        weatherList.add(weather)
        val mockWeatherDate: WeatherModel = WeatherModel(
            coord = Coord(12.9762,77.6033),
            main = MainData(temp = 12.75),
            weather = weatherList
        )

        // make the github api to return mock data
        Mockito.`when`(weatherRepo.getWeatherData(cityName))
            .thenReturn(Single.just(mockWeatherDate))

        // observe on the MutableLiveData with an observer
        secondFragmentViewModel.navigationCommand.observeForever(observer)
        secondFragmentViewModel.getWeatherData(cityName)

        assertEquals(SecondFragmentViewModel.UPDATE_WEATHER_DATA, secondFragmentViewModel.navigationCommand.value)
    }

    @Test
    fun `test getWeatherData with failure`() {

        // make the github api to return mock data
        Mockito.`when`(weatherRepo.getWeatherData(""))
            .thenReturn(Single.error(IOException()))

        // observe on the MutableLiveData with an observer
        secondFragmentViewModel.navigationCommand.observeForever(observer)
        secondFragmentViewModel.getWeatherData("")

        assertEquals(SecondFragmentViewModel.UPDATE_ERROR, secondFragmentViewModel.navigationCommand.value)
    }


    @Test
    fun `test onBackPressed`() {
        secondFragmentViewModel.onBackPressed()
        assertEquals(SecondFragmentViewModel.NAV_BACK, secondFragmentViewModel.navigationCommand.value)
    }

}

private fun <T> SingleLiveEvent<T>.observeForever(observer: Observer<SingleLiveEvent<T>>) {

}
