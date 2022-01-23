package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class AddNewCityInListUseCaseTest {

    private lateinit var useCase: AddNewCityInListUseCase
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = AddNewCityInListUseCase(repository)
    }

    @Test
    fun `WHEN invoke use case EXPECT call addNewCityInList from repository`() {
        runBlocking {
            useCase.invoke("Москва")
            verify(repository, times(1)).addNewCityInList("Москва")
        }
    }
}