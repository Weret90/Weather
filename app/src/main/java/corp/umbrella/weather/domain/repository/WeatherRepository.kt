package corp.umbrella.weather.domain.repository

import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.utils.Result

interface WeatherRepository {

    suspend fun getWeatherList(): List<Weather>?
    suspend fun updateWeatherList(weatherList: List<Weather>): Result
    suspend fun addNewCity(cityName: String): Result
}