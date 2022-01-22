package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class UpdateWeatherListUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke() {
        repository.updateWeatherList()
    }
}