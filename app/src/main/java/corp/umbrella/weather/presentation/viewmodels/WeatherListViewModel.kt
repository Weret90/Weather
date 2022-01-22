package corp.umbrella.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.umbrella.weather.domain.usecases.DeleteCityFromListUseCase
import corp.umbrella.weather.domain.usecases.GetWeatherListLiveDataUseCase
import corp.umbrella.weather.domain.usecases.UpdateWeatherListUseCase
import corp.umbrella.weather.presentation.utils.LoadDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    getWeatherListLiveDataUseCase: GetWeatherListLiveDataUseCase,
    private val updateWeatherListUseCase: UpdateWeatherListUseCase,
    private val deleteCityFromListUseCase: DeleteCityFromListUseCase
) : ViewModel() {

    private val _loadDataStateLiveData = MutableLiveData<LoadDataState?>()
    val loadDataStateLiveData: LiveData<LoadDataState?> get() = _loadDataStateLiveData

    val weatherListLiveData = getWeatherListLiveDataUseCase()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadDataStateLiveData.value = LoadDataState.Error(throwable)
    }

    init {
        updateWeatherList()
    }

    fun deleteCityFromList(cityId: Int) {
        viewModelScope.launch {
            deleteCityFromListUseCase(cityId)
        }
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