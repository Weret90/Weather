package corp.umbrella.weather.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import corp.umbrella.weather.data.local.database.WeatherDao
import corp.umbrella.weather.data.mapper.WeatherMapper
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.data.remote.models.WeatherDto
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dao: WeatherDao,
    private val api: ApiService,
    private val mapper: WeatherMapper
) : WeatherRepository {

    override fun getWeatherListLiveData(): LiveData<List<Weather>> {
        return Transformations.map(dao.getWeatherListLiveData()) { list ->
            list.map { mapper.mapDbModelToDomainEntity(it) }
        }
    }

    override suspend fun addNewCityInList(cityName: String) {
        val newCityWeatherInfo = api.getWeather(cityName)
        dao.insertWeather(mapper.mapDtoToDbModel(newCityWeatherInfo))
    }

    override suspend fun updateWeatherList() {
        coroutineScope {
            val cities = getCities()
            val deferredWeatherList = mutableListOf<Deferred<WeatherDto>>().apply {
                cities.forEach { cityName ->
                    add(async { api.getWeather(cityName) })
                }
            }
            val freshWeatherList = deferredWeatherList.awaitAll()
            dao.insertWeatherList(freshWeatherList.map { mapper.mapDtoToDbModel(it) })
        }
    }

    override suspend fun deleteCityFromList(cityId: Int) {
        dao.deleteWeather(cityId)
    }

    private suspend fun getCities(): List<String> {
        return dao.getWeatherList().map {
            it.cityName
        }
    }
}