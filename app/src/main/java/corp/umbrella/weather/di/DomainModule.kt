package corp.umbrella.weather.di

import corp.umbrella.weather.data.repository.WeatherRepositoryImpl
import corp.umbrella.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun bindRepository(impl: WeatherRepositoryImpl): WeatherRepository
}