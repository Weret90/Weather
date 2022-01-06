package corp.umbrella.weather.domain.utils

sealed class Result {
    data class Error(val error: Throwable) : Result()
    object Success : Result()
}