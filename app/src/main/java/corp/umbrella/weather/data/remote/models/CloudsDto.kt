package corp.umbrella.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all")
    val cloudsPercent: Int
)