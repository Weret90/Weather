package corp.umbrella.weather.di

import androidx.room.Room
import corp.umbrella.weather.data.local.database.WeatherDatabase
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.data.remote.network.WeatherInterceptor
import corp.umbrella.weather.data.repository.WeatherRepositoryImpl
import corp.umbrella.weather.domain.repository.WeatherRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataDi {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val DB_NAME = "weather.db"

    val retrofitModule = module {
        single<OkHttpClient> {
            OkHttpClient().newBuilder()
                .addInterceptor(WeatherInterceptor)
                .build()
        }

        single<ApiService> {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

    val roomModule = module {
        single {
            Room.databaseBuilder(get(), WeatherDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
                .weatherDao()
        }
    }

    val repositoryModule = module {
        single<WeatherRepository> {
            WeatherRepositoryImpl(dao = get(), api = get())
        }
    }
}