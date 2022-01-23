package corp.umbrella.weather.domain.usecases

import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class UpdateWeatherListUseCaseTest {
    private lateinit var useCase: UpdateWeatherListUseCase
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = UpdateWeatherListUseCase(repository)
    }

    @Test
    fun `WHEN invoke use case EXPECT call updateWeatherList from repository`() {
        runBlocking {
            useCase.invoke()
            verify(repository, times(1)).updateWeatherList()
        }
    }
}