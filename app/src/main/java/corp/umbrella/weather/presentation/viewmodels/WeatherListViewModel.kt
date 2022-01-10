package corp.umbrella.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.usecases.GetWeatherListLiveDataUseCase
import corp.umbrella.weather.domain.usecases.UpdateWeatherListUseCase
import corp.umbrella.weather.domain.utils.Result
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val getWeatherListLiveDataUseCase: GetWeatherListLiveDataUseCase,
    private val updateWeatherListUseCase: UpdateWeatherListUseCase,
) : ViewModel() {

    private val _loadingBarLiveData = MutableLiveData<Boolean>()
    val loadingBarLiveData: LiveData<Boolean> = _loadingBarLiveData
    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    fun getWeatherListLiveData(): LiveData<List<Weather>> {
        updateWeatherList()
        return getWeatherListLiveDataUseCase()
    }

    private fun updateWeatherList() {
        viewModelScope.launch {
            _loadingBarLiveData.value = true
            val result = updateWeatherListUseCase()
            if (result is Result.Error) {
                _errorLiveData.value = result.error
            }
            _loadingBarLiveData.value = false
        }
    }

    fun clearLiveData() {
        _errorLiveData.value = null
        _loadingBarLiveData.value = null
    }
}