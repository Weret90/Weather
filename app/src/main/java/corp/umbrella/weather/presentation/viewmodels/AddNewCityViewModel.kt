package corp.umbrella.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.umbrella.weather.domain.usecases.AddNewCityInListUseCase
import corp.umbrella.weather.presentation.utils.CityNameValidator
import corp.umbrella.weather.presentation.utils.LoadDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewCityViewModel @Inject constructor(
    private val addNewCityInListUseCase: AddNewCityInListUseCase,
) : ViewModel() {

    private val _incorrectCityNameLiveData = MutableLiveData<Unit?>()
    val incorrectCityNameLiveData: LiveData<Unit?> get() = _incorrectCityNameLiveData

    private val _loadDataStateLiveData = MutableLiveData<LoadDataState?>()
    val loadDataStateLiveData: LiveData<LoadDataState?> get() = _loadDataStateLiveData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadDataStateLiveData.value = LoadDataState.Error(throwable)
    }

    fun addNewCityInList(cityName: String?) {
        viewModelScope.launch(coroutineExceptionHandler) {
            if (CityNameValidator.isCorrectCityName(cityName?.trim())) {
                _loadDataStateLiveData.value = LoadDataState.Loading
                addNewCityInListUseCase(cityName!!.trim().lowercase())
                _loadDataStateLiveData.value = LoadDataState.Success
            } else {
                _incorrectCityNameLiveData.value = Unit
            }
        }
    }

    fun clearLiveData() {
        _incorrectCityNameLiveData.value = null
        _loadDataStateLiveData.value = null
    }
}