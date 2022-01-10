package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import corp.umbrella.weather.domain.utils.Result

class UpdateWeatherListUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): Result = repository.updateWeatherList()
}