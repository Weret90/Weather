package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class DeleteCityFromListUseCaseTest {
    private lateinit var useCase: DeleteCityFromListUseCase
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = DeleteCityFromListUseCase(repository)
    }

    @Test
    fun `WHEN invoke use case EXPECT call deleteCityFromList from repository`() {
        runBlocking {
            useCase.invoke(12)
            verify(repository, times(1)).deleteCityFromList(12)
        }
    }
}