package corp.umbrella.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.umbrella.weather.domain.usecases.AddNewCityUseCase
import corp.umbrella.weather.domain.utils.Result
import corp.umbrella.weather.presentation.utils.CityNameValidator
import kotlinx.coroutines.launch

class AddNewCityViewModel(
    private val addNewCityUseCase: AddNewCityUseCase,
) : ViewModel() {

    private val _incorrectCityNameLiveData = MutableLiveData<Unit>()
    val incorrectCityNameLiveData: LiveData<Unit> = _incorrectCityNameLiveData
    private val _loadingBarLiveData = MutableLiveData<Boolean>()
    val loadingBarLiveData: LiveData<Boolean> = _loadingBarLiveData
    private val _addNewCityResultLiveDate = MutableLiveData<Result>()
    val addNewCityResultLiveDate: LiveData<Result> = _addNewCityResultLiveDate

    fun addNewCity(cityName: String?) {
        viewModelScope.launch {
            if (CityNameValidator.isCorrectCityName(cityName?.trim())) {
                _loadingBarLiveData.value = true
                val result = addNewCityUseCase(cityName!!.trim().lowercase())
                _addNewCityResultLiveDate.value = result
                _loadingBarLiveData.value = false
            } else {
                _incorrectCityNameLiveData.value = Unit
            }
        }
    }

    fun clearLiveData() {
        _incorrectCityNameLiveData.value = null
        _addNewCityResultLiveDate.value = null
        _loadingBarLiveData.value = null
    }
}