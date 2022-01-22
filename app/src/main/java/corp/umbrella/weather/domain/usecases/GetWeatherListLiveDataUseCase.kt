package corp.umbrella.weather.domain.usecases

import androidx.lifecycle.LiveData
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherListLiveDataUseCase @Inject constructor(private val repository: WeatherRepository) {

     operator fun invoke(): LiveData<List<Weather>> = repository.getWeatherListLiveData()
}