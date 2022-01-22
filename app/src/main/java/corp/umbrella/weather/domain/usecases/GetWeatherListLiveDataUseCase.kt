package corp.umbrella.weather.domain.usecases

import androidx.lifecycle.LiveData
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.repository.WeatherRepository

class GetWeatherListLiveDataUseCase(private val repository: WeatherRepository) {

     operator fun invoke(): LiveData<List<Weather>> = repository.getWeatherListLiveData()
}