package com.parul.imdbapplication

import com.parul.imdbapplication.data.mappers.WeatherModelMapper
import com.parul.imdbapplication.data.model.MainData
import com.parul.imdbapplication.data.model.Weather
import com.parul.imdbapplication.data.model.WeatherModel
import com.parul.imdbapplication.domain.model.WeatherConciseDetails
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherModelMapperTest {

    @Mock
    lateinit var weatherModelMapper: WeatherModelMapper

    private lateinit var weatherMockData: WeatherModel

    @Before
    fun setUp() {
        // initialize the mapper
        weatherModelMapper = WeatherModelMapper()


    }

    @Test
    fun `test toWeatherConciseDetails success`() {
        weatherMockData = mock(WeatherModel::class.java)

        val weather: Weather = mock(Weather::class.java)
        val weatherList: MutableList<Weather> = ArrayList<Weather>(5)
        weatherList.add(weather)

        val mainData: MainData = mock(MainData::class.java)

        `when`(weatherMockData.weather).thenReturn(weatherList)
        `when`(weatherMockData.main).thenReturn(mainData)
        `when`(weatherMockData.weather[0].main).thenReturn("Cloud")
        `when`(weatherMockData.weather[0].description).thenReturn("scattered clouds")
        `when`(weatherMockData.weather[0].icon).thenReturn("03n")
        `when`(weatherMockData.main.temp).thenReturn(24.56)
        val data: WeatherConciseDetails = weatherModelMapper.toWeatherConciseDetails(weatherMockData)
        assertThat(data, `is`(instanceOf(WeatherConciseDetails::class.java)))
    }

}