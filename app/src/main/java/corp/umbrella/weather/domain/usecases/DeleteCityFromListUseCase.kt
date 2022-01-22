package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteCityFromListUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(cityId: Int) {
        repository.deleteCityFromList(cityId)
    }
}