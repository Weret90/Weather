package corp.umbrella.weather.di

import corp.umbrella.weather.presentation.viewmodels.AddNewCityViewModel
import corp.umbrella.weather.presentation.viewmodels.WeatherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationDi {

    val viewModelsModule = module {
        viewModel {
            WeatherListViewModel(getWeatherListLiveDataUseCase = get(), updateWeatherListUseCase = get())
        }

        viewModel {
            AddNewCityViewModel(addNewCityInListUseCase = get())
        }
    }
}