package corp.umbrella.weather.di

import android.content.Context
import androidx.room.Room
import corp.umbrella.weather.data.local.database.WeatherDao
import corp.umbrella.weather.data.local.database.WeatherDatabase
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.data.remote.network.WeatherInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val DB_NAME = "weather.db"
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(WeatherInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(@ApplicationContext context: Context): WeatherDao {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
            .weatherDao()
    }
}