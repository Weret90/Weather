package corp.umbrella.weather.di

import corp.umbrella.weather.domain.usecases.AddNewCityInListUseCase
import corp.umbrella.weather.domain.usecases.GetWeatherListLiveDataUseCase
import corp.umbrella.weather.domain.usecases.UpdateWeatherListUseCase
import org.koin.dsl.module

object DomainDi {

    val useCasesModule = module {
        factory {
            AddNewCityInListUseCase(repository = get())
        }
        factory {
            GetWeatherListLiveDataUseCase(repository = get())
        }
        factory {
            UpdateWeatherListUseCase(repository = get())
        }
    }
}