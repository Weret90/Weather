package corp.umbrella.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.usecases.GetWeatherListLiveDataUseCase
import corp.umbrella.weather.domain.usecases.UpdateWeatherListUseCase
import corp.umbrella.weather.presentation.utils.LoadDataState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val getWeatherListLiveDataUseCase: GetWeatherListLiveDataUseCase,
    private val updateWeatherListUseCase: UpdateWeatherListUseCase,
) : ViewModel() {

    private val _loadDataStateLiveData = MutableLiveData<LoadDataState?>()
    val loadDataStateLiveData: LiveData<LoadDataState?> get() = _loadDataStateLiveData

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _loadDataStateLiveData.value = LoadDataState.Error(throwable)
        }

    fun getWeatherListLiveData(): LiveData<List<Weather>> {
        updateWeatherList()
        return getWeatherListLiveDataUseCase()
    }

    private fun updateWeatherList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _loadDataStateLiveData.value = LoadDataState.Loading
            updateWeatherListUseCase()
            _loadDataStateLiveData.value = LoadDataState.Success
        }
    }

    fun clearLiveData() {
        _loadDataStateLiveData.value = null
    }
}