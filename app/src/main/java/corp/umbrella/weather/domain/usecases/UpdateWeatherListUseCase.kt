package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository

class UpdateWeatherListUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke() {
        repository.updateWeatherList()
    }
}