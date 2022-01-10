package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import corp.umbrella.weather.domain.utils.Result

class AddNewCityUseCase (private val repository: WeatherRepository) {
    suspend operator fun invoke(cityName: String): Result = repository.addNewCity(cityName)
}