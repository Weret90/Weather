package corp.umbrella.weather.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import corp.umbrella.weather.data.local.WeatherDao
import corp.umbrella.weather.data.mapper.WeatherMapper
import corp.umbrella.weather.data.remote.ApiService
import corp.umbrella.weather.data.remote.models.WeatherDto
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.repository.WeatherRepository
import corp.umbrella.weather.domain.utils.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class WeatherRepositoryImpl(
    private val dao: WeatherDao,
    private val api: ApiService,
) : WeatherRepository {

    override fun getWeatherListLiveData(): LiveData<List<Weather>> {
        return Transformations.map(dao.getWeatherListLiveData()) { list ->
            list.map { WeatherMapper.mapDbModelToDomainEntity(it) }
        }
    }

    override suspend fun addNewCity(cityName: String): Result {
        return try {
            val newCityWeather = api.getWeather(cityName)
            dao.insertWeather(WeatherMapper.mapDtoToDbModel(newCityWeather))
            Result.Success
        } catch (error: Throwable) {
            Result.Error(error)
        }
    }

    override suspend fun updateWeatherList(): Result {
        return try {
            coroutineScope {
                val cities = getCities()
                val deferredList = mutableListOf<Deferred<WeatherDto>>().apply {
                    cities.forEach { cityName ->
                        add(async { api.getWeather(cityName) })
                    }
                }
                val freshWeatherList = deferredList.awaitAll()
                dao.insertWeatherList(freshWeatherList.map { WeatherMapper.mapDtoToDbModel(it) })
                Result.Success
            }
        } catch (error: Throwable) {
            Result.Error(error)
        }
    }

    private suspend fun getCities(): List<String> {
        return dao.getWeatherList().map {
            it.cityName
        }
    }
}