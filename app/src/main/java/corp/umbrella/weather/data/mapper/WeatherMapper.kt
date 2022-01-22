package corp.umbrella.weather.data.mapper

import corp.umbrella.weather.data.local.models.WeatherDbModel
import corp.umbrella.weather.data.remote.models.WeatherDto
import corp.umbrella.weather.domain.entities.Weather
import java.text.SimpleDateFormat
import java.util.*

class WeatherMapper {

    companion object {
        private const val DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"
        private const val WEATHER_ICON_URL = "https://openweathermap.org/img/wn/%s@2x.png"
        private const val MILLIS_IN_SECOND = 1000
        private const val SINGLE_ELEMENT_INDEX = 0
        private const val SIGN_PERCENT = " %"
        private const val SIGN_PRESSURE = " кПа"
        private const val SIGN_TEMP_CELSIUS = " °C"
        private const val SIGN_WIND_SPEED = " м/с"

        fun mapDtoToDbModel(dto: WeatherDto) = WeatherDbModel(
            cityId = dto.cityId,
            cityName = dto.cityName,
            timeOfDateUnix = dto.timeOfDateUnix,
            cloudsPercent = dto.cloudsDto.cloudsPercent,
            tempFeelsLike = dto.mainInfoDto.feelsLike,
            humidity = dto.mainInfoDto.humidity,
            pressure = dto.mainInfoDto.pressure,
            temp = dto.mainInfoDto.temp,
            description = dto.weatherInfoDto[SINGLE_ELEMENT_INDEX].description,
            weatherIcon = dto.weatherInfoDto[SINGLE_ELEMENT_INDEX].icon,
            windSpeed = dto.windDto.speed
        )

        fun mapDbModelToDomainEntity(dbModel: WeatherDbModel) = Weather(
            cityId = dbModel.cityId.toString(),
            cityName = dbModel.cityName,
            timeOfDate = mapTimeOfDateUnixToString(dbModel.timeOfDateUnix),
            cloudsPercent = dbModel.cloudsPercent.toString() + SIGN_PERCENT,
            humidity = dbModel.humidity.toString() + SIGN_PERCENT,
            tempFeelsLike = dbModel.humidity.toString() + SIGN_TEMP_CELSIUS,
            pressure = dbModel.pressure.toString() + SIGN_PRESSURE,
            temperature = dbModel.temp.toString() + SIGN_TEMP_CELSIUS,
            description = dbModel.description,
            weatherIconUrl = String.format(WEATHER_ICON_URL, dbModel.weatherIcon),
            windSpeed = dbModel.windSpeed.toString() + SIGN_WIND_SPEED
        )

        private fun mapTimeOfDateUnixToString(timeOfDateUnix: Int): String {
            val sdf = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
            val date = Date(timeOfDateUnix.toLong() * MILLIS_IN_SECOND)
            return sdf.format(date)
        }
    }
}