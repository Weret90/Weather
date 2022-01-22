package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository

class AddNewCityInListUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(cityName: String) {
        repository.addNewCityInList(cityName)
    }
}