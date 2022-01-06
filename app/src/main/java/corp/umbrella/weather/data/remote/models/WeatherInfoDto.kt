package corp.umbrella.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class WeatherInfoDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)