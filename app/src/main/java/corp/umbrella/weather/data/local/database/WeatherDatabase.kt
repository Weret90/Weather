package corp.umbrella.weather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import corp.umbrella.weather.data.local.models.WeatherDbModel

@Database(entities = [WeatherDbModel::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}