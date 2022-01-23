package corp.umbrella.weather.kaspresso.screens

import corp.umbrella.weather.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object AddNewCityScreen : Screen<AddNewCityScreen>() {

    val insertCityButton = KButton { withId(R.id.button_insert_new_city) }
    val cityNameInputField = KEditText { withId(R.id.city_name_input_field) }
}