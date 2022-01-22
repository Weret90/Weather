package corp.umbrella.weather.data.remote.network

import corp.umbrella.weather.data.remote.models.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(@Query("q") cityName: String): WeatherDto
}