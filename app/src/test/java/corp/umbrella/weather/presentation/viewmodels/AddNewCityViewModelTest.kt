package corp.umbrella.weather.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import corp.umbrella.weather.domain.usecases.AddNewCityInListUseCase
import corp.umbrella.weather.presentation.utils.CityNameValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class AddNewCityViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddNewCityViewModel
    private lateinit var useCase: AddNewCityInListUseCase
    private lateinit var validator: CityNameValidator

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        useCase = mock()
        validator = CityNameValidator()
        viewModel = AddNewCityViewModel(useCase, validator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `WHEN add new city in list EXPECT validator is check city name`() {
        runBlocking {
            val validatorMock: CityNameValidator = mock()
            val viewModelWithMockValidator = AddNewCityViewModel(useCase, validatorMock)
            viewModelWithMockValidator.addNewCityInList("Москва")
            verify(validatorMock, times(1)).isCorrectCityName("Москва")
        }
    }

    @Test
    fun `WHEN try to add city with empty name EXPECT useCase method is never call`() {
        runBlocking {
            viewModel.addNewCityInList("")
            verify(useCase, never()).invoke(any())
        }
    }

    @Test
    fun `WHEN try to add city with name contains numbers EXPECT useCase method is never call`() {
        runBlocking {
            viewModel.addNewCityInList("Москва12")
            verify(useCase, never()).invoke(any())
        }
    }

    @Test
    fun `WHEN try to add city with name contains english letters EXPECT useCase method is never call`() {
        runBlocking {
            viewModel.addNewCityInList("Moscow")
            verify(useCase, never()).invoke(any())
        }
    }

    @Test
    fun `WHEN add city with correct name EXPECT call useCase method`() {
        runBlocking {
            viewModel.addNewCityInList("Москва")
            verify(useCase, times(1)).invoke(any())
        }
    }
}
