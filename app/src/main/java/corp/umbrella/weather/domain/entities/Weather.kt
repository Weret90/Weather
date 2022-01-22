package corp.umbrella.weather.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val cityId: Int,
    val cityName: String,
    val timeOfDate: String,
    val cloudsPercent: String,
    val tempFeelsLike: String,
    val humidity: String,
    val pressure: String,
    val temperature: String,
    val description: String,
    val weatherIconUrl: String,
    val windSpeed: String,
) : Parcelable