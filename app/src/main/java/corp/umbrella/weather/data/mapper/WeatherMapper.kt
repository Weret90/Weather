package corp.umbrella.weather.data.mapper

import corp.umbrella.weather.data.local.WeatherDbModel
import corp.umbrella.weather.data.remote.models.WeatherDto
import corp.umbrella.weather.domain.entities.Weather
import java.text.SimpleDateFormat
import java.util.*

class WeatherMapper {

    companion object {
        private const val DATE_TIME_PATTERN = "dd:MM:yyyy HH:mm"
        private const val WEATHER_ICON_URL = "http://openweathermap.org/img/wn/%s@2x.png"

        fun mapDtoToDbModel(dto: WeatherDto) = WeatherDbModel(
            cityId = dto.cityId,
            cityName = dto.cityName,
            timeOfDateUnix = dto.timeOfDateUnix,
            cloudsPercent = dto.cloudsDto.cloudsPercent,
            tempFeelsLike = dto.mainInfoDto.feelsLike,
            humidity = dto.mainInfoDto.humidity,
            pressure = dto.mainInfoDto.pressure,
            temp = dto.mainInfoDto.temp,
            description = dto.weatherInfoDto[0].description,
            weatherIcon = dto.weatherInfoDto[0].icon,
            windSpeed = dto.windDto.speed
        )

        fun mapDbModelToDomainEntity(dbModel: WeatherDbModel) = Weather(
            cityId = dbModel.cityId.toString(),
            cityName = dbModel.cityName,
            timeOfDate = mapTimeOfDateUnixToString(dbModel.timeOfDateUnix),
            cloudsPercent = dbModel.cloudsPercent.toString(),
            humidity = dbModel.humidity.toString(),
            tempFeelsLike = dbModel.humidity.toString(),
            pressure = dbModel.pressure.toString(),
            temperature = dbModel.temp.toString(),
            description = dbModel.description,
            weatherIconUrl = String.format(WEATHER_ICON_URL, dbModel.weatherIcon),
            windSpeed = dbModel.toString()
        )

        private fun mapTimeOfDateUnixToString(timeOfDateUnix: Int): String {
            val sdf = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
            val date = Date(timeOfDateUnix.toLong() * 1000)
            return sdf.format(date)
        }
    }
}