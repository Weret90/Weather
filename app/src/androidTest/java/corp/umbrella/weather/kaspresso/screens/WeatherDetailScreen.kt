package corp.umbrella.weather.kaspresso.screens

import corp.umbrella.weather.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

object WeatherDetailScreen : Screen<WeatherDetailScreen>() {

    val cityName = KTextView { withId(R.id.weather_detail_city_name) }
    val icon = KImageView { withId(R.id.weather_status_icon) }
    val temp = KTextView { withId(R.id.weather_detail_temp) }
    val clouds = KTextView { withId(R.id.clouds) }
    val humidity = KTextView { withId(R.id.humidity) }
    val wind = KTextView { withId(R.id.wind) }
    val pressure = KTextView { withId(R.id.pressure) }
}