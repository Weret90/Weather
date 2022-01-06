package corp.umbrella.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class SnowDto(
    @SerializedName("1h")
    val snowVolumeForHour: Double
)