package corp.umbrella.weather.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import corp.umbrella.weather.data.local.models.WeatherDbModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getWeatherListLiveData(): LiveData<List<WeatherDbModel>>

    @Query("SELECT * FROM weather")
    suspend fun getWeatherList(): List<WeatherDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherList(weatherList: List<WeatherDbModel>)
}