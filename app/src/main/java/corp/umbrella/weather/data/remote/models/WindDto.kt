package corp.umbrella.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("deg")
    val direction: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)