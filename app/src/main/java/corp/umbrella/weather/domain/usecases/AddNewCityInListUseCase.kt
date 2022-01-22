package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class AddNewCityInListUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(cityName: String) {
        repository.addNewCityInList(cityName)
    }
}