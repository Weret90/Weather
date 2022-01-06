package corp.umbrella.weather.domain.entities

data class Weather(
    val cityId: String,
    val cityName: String,
    val timeOfDate: String,
    val cloudsPercent: String,
    val tempFeelsLike: String,
    val humidity: String,
    val pressure: String,
    val temperature: String,
    val description: String,
    val weatherIconUrl: String,
    val windSpeed: String
)