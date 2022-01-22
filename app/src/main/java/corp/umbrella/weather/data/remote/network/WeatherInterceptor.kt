package corp.umbrella.weather.data.remote.network

import corp.umbrella.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object WeatherInterceptor : Interceptor {

    private const val QUERY_PARAM_API_KEY = "appid"
    private const val API_KEY = BuildConfig.WEATHER_API_KEY
    private const val QUERY_PARAM_LANGUAGE = "lang"
    private const val LANGUAGE = "ru"
    private const val QUERY_PARAM_UNITS = "units"
    private const val UNITS = "metric"

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url().newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, API_KEY)
            .addQueryParameter(QUERY_PARAM_LANGUAGE, LANGUAGE)
            .addQueryParameter(QUERY_PARAM_UNITS, UNITS)
            .build()
        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}