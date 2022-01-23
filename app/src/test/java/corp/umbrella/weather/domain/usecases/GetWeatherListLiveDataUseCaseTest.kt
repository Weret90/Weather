package corp.umbrella.weather.domain.usecases

import androidx.lifecycle.LiveData
import corp.umbrella.weather.domain.entities.Weather
import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetWeatherListLiveDataUseCaseTest {
    private lateinit var useCase: GetWeatherListLiveDataUseCase
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = mock()
        val liveDataMock: LiveData<List<Weather>> = mock()
        whenever(repository.getWeatherListLiveData()).thenReturn(liveDataMock)
        useCase = GetWeatherListLiveDataUseCase(repository)
    }

    @Test
    fun `WHEN invoke use case EXPECT call getWeatherListLiveData from repository`() {
        runBlocking {
            useCase.invoke()
            verify(repository, times(1)).getWeatherListLiveData()
        }
    }

    @Test
    fun `WHEN invoke use case EXPECT get not null liveData`() {
        runBlocking {
            assertNotNull(useCase.invoke())
        }
    }
}