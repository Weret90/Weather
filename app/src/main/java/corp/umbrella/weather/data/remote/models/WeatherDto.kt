package corp.umbrella.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("clouds")
    val cloudsDto: CloudsDto,
    @SerializedName("dt")
    val timeOfDateUnix: Int,
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("main")
    val mainInfoDto: MainInfoDto,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("snow")
    val snowDto: SnowDto?,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weatherInfoDto: List<WeatherInfoDto>,
    @SerializedName("wind")
    val windDto: WindDto
)

