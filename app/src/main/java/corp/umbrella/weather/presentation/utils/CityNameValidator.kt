package corp.umbrella.weather.presentation.utils

import java.util.regex.Pattern
import javax.inject.Inject

class CityNameValidator @Inject constructor() {
    companion object {
        private const val CITY_NAME_PATTERN = "^[а-яА-Я-]{3,}$"
    }

    fun isCorrectCityName(cityName: String?): Boolean {
        return (cityName != null
                && cityName.isNotBlank()
                && Pattern.compile(CITY_NAME_PATTERN).matcher(cityName).matches())
    }
}