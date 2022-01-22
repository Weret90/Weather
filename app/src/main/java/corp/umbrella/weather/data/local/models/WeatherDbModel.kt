package corp.umbrella.weather.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDbModel(
    @PrimaryKey
    val cityId: Int,
    val cityName: String,
    val timeOfDateUnix: Int,
    val cloudsPercent: Int,
    val tempFeelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val description: String,
    val weatherIcon: String,
    val windSpeed: Double
)