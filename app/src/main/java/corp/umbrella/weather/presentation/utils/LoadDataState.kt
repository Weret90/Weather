package corp.umbrella.weather.presentation.utils

sealed class LoadDataState {

    object Loading : LoadDataState()
    data class Error(val throwable: Throwable) : LoadDataState()
    object Success : LoadDataState()
}