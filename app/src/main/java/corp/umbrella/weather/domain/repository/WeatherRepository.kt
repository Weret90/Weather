package corp.umbrella.weather.domain.repository

import androidx.lifecycle.LiveData
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.utils.Result

interface WeatherRepository {

    fun getWeatherListLiveData(): LiveData<List<Weather>>
    suspend fun addNewCity(cityName: String): Result
    suspend fun updateWeatherList(): Result
}