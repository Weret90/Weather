package corp.umbrella.weather.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import corp.umbrella.weather.domain.usecases.DeleteCityFromListUseCase
import corp.umbrella.weather.domain.usecases.GetWeatherListLiveDataUseCase
import corp.umbrella.weather.domain.usecases.UpdateWeatherListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class WeatherListViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherListViewModel
    private lateinit var getWeatherListLiveDataUseCase: GetWeatherListLiveDataUseCase
    private lateinit var updateWeatherListUseCase: UpdateWeatherListUseCase
    private lateinit var deleteCityFromListUseCase: DeleteCityFromListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getWeatherListLiveDataUseCase = mock()
        updateWeatherListUseCase = mock()
        deleteCityFromListUseCase = mock()
        viewModel = WeatherListViewModel(
            getWeatherListLiveDataUseCase, updateWeatherListUseCase, deleteCityFromListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `WHEN add new city in list EXPECT validator is check city name`() {
        runBlocking {
            viewModel.deleteCityFromList(12)
            verify(deleteCityFromListUseCase, times(1)).invoke(12)
        }
    }

    @Test
    fun `WHEN viewModel is init EXPECT updateWeatherListUseCase method call`() {
        runBlocking {
            viewModel.weatherListLiveData
            verify(updateWeatherListUseCase, times(1)).invoke()
        }
    }
}