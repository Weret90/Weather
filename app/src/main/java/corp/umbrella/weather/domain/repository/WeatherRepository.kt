package corp.umbrella.weather.domain.repository

import androidx.lifecycle.LiveData
import corp.umbrella.weather.domain.entities.Weather

interface WeatherRepository {

    fun getWeatherListLiveData(): LiveData<List<Weather>>
    suspend fun addNewCityInList(cityName: String)
    suspend fun updateWeatherList()
    suspend fun deleteCityFromList(cityId: Int)
}