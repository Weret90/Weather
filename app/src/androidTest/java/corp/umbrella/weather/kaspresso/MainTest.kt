package corp.umbrella.weather.kaspresso

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import corp.umbrella.weather.R
import corp.umbrella.weather.kaspresso.annotation.TestCase
import corp.umbrella.weather.kaspresso.screens.AddNewCityScreen
import corp.umbrella.weather.kaspresso.screens.WeatherDetailScreen
import corp.umbrella.weather.kaspresso.screens.WeatherListScreen
import corp.umbrella.weather.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest : KTestCase() {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    @TestCase(
        name = "Main Test",
        description = "Проверка правильной работы приложения (перед запуском убедитесь что начальный экран пуст (без городов))"
    )
    fun checkApp() {
        run {
            step("Проверка начального экрана со списком городов") {
                WeatherListScreen {
                    addNewCityButton {
                        isVisible()
                        isDisplayed()
                        isClickable()
                    }
                    weatherList {
                        hasSize(0)
                    }
                }
            }

            step("Переход на второй экран, где можно добавить город") {
                WeatherListScreen {
                    addNewCityButton {
                        click()
                    }
                }
            }

            step("Проверка второго экрана, где можно добавить город") {
                AddNewCityScreen {
                    cityNameInputField {
                        isVisible()
                        isDisplayed()
                        hasHint("Введите название города")
                    }
                    insertCityButton {
                        isVisible()
                        isDisplayed()
                        isClickable()
                        hasText("Добавить город")
                    }
                }
            }

            step("Попытка добавления города с пустым названием, перехода обратно быть не должно") {
                AddNewCityScreen {
                    insertCityButton {
                        click()
                    }
                    cityNameInputField {
                        isDisplayed()
                    }
                    insertCityButton {
                        isDisplayed()
                    }
                }
            }

            step("Попытка добавления города с неправильным названием, перехода обратно быть не должно") {
                AddNewCityScreen {
                    cityNameInputField {
                        typeText("Moscow123")
                    }
                    closeSoftKeyboard()
                    insertCityButton {
                        click()
                    }
                    cityNameInputField {
                        isDisplayed()
                    }
                    insertCityButton {
                        isDisplayed()
                    }
                }
            }

            step("Попытка добавления города с правильным названием, должен быть переход к начальному экрану") {
                AddNewCityScreen {
                    cityNameInputField {
                        replaceText("Москва")
                    }
                    closeSoftKeyboard()
                    insertCityButton {
                        click()
                    }
                }
            }

            step("Проверка экрана с городами: должен быть один город") {
                WeatherListScreen {
                    weatherList {
                        hasSize(1)
                        childAt<WeatherListScreen.WeatherItem>(0) {
                            cityName {
                                isDisplayed()
                                hasText("Москва")
                                hasTextColor(R.color.white)
                            }
                            icon {
                                isDisplayed()
                            }
                            dateAndTime {
                                isDisplayed()
                                hasTextColor(R.color.white)
                            }
                            temp {
                                isDisplayed()
                                hasTextColor(R.color.white)
                            }
                        }
                    }
                }
            }

            step("Переход на экран с детальной информацией о погоде") {
                WeatherListScreen {
                    weatherList.firstChild<WeatherListScreen.WeatherItem> {
                        click()
                    }
                }
            }

            step("Проверка экрана с детальной информацией о погоде") {
                WeatherDetailScreen {
                    cityName {
                        isDisplayed()
                        hasText("Москва")
                        hasTextColor(R.color.white)
                    }
                    icon.isDisplayed()
                    temp {
                        isDisplayed()
                        hasTextColor(R.color.white)
                    }
                    humidity {
                        isDisplayed()
                        hasTextColor(R.color.white)
                    }
                    pressure {
                        isDisplayed()
                        hasTextColor(R.color.white)
                    }
                    wind {
                        isDisplayed()
                        hasTextColor(R.color.white)
                    }
                    clouds {
                        isDisplayed()
                        hasTextColor(R.color.white)
                    }
                }
            }

            step("Переход обратно на начальный экран с городами") {
                WeatherDetailScreen.pressBack()
            }

            step("Проверка удаления города из списка") {
                WeatherListScreen {
                    weatherList {
                        firstChild<WeatherListScreen.WeatherItem> {
                            longClick()
                        }
                        hasSize(0)
                    }
                }
            }
        }
    }
}