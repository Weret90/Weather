package corp.umbrella.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    suspend fun getWeatherList(): List<WeatherDbModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherList(weatherList: List<WeatherDbModel>)
}