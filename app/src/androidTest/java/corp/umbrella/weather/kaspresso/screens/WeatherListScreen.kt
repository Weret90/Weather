package corp.umbrella.weather.kaspresso.screens

import android.view.View
import corp.umbrella.weather.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object WeatherListScreen : Screen<WeatherListScreen>() {

    val addNewCityButton = KButton { withId(R.id.button_add_new_city) }
    val weatherList = KRecyclerView(
        builder = { withId(R.id.weather_list_rv) },
        itemTypeBuilder = { itemType(WeatherListScreen::WeatherItem) }
    )

    class WeatherItem(parent: Matcher<View>) : KRecyclerItem<WeatherItem>(parent) {
        val cityName = KTextView(parent) { withId(R.id.city_name) }
        val dateAndTime = KTextView(parent) { withId(R.id.date_time) }
        val temp = KTextView(parent) { withId(R.id.temperature) }
        val icon = KImageView(parent) { withId(R.id.weather_status_icon) }
    }
}