package corp.umbrella.weather.data.repository

import corp.umbrella.weather.data.local.database.WeatherDao
import corp.umbrella.weather.data.local.models.WeatherDbModel
import corp.umbrella.weather.data.mapper.WeatherMapper
import corp.umbrella.weather.data.remote.models.WeatherDto
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepository
    private lateinit var dao: WeatherDao
    private lateinit var api: ApiService
    private lateinit var mapper: WeatherMapper

    @Before
    fun setUp() {
        dao = mock()
        api = mock()
        mapper = mock()
        repository = WeatherRepositoryImpl(dao, api, mapper)
    }

    @Test(expected = Exception::class)
    fun `WHEN api return error EXPECT exception`() {
        runBlocking {
            whenever(api.getWeather(any())).thenThrow(Exception())
            repository.addNewCityInList(any())
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT api send get request`() {
        runBlocking {
            val weatherDto: WeatherDto = mock()
            whenever(api.getWeather("Москва")).thenReturn(weatherDto)
            repository.addNewCityInList("Москва")
            verify(api, times(1)).getWeather("Москва")
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT dao insert weather in list`() {
        runBlocking {
            val weatherDto: WeatherDto = mock()
            val weatherDbModel: WeatherDbModel = mock()
            whenever(api.getWeather("Москва")).thenReturn(weatherDto)
            whenever(mapper.mapDtoToDbModel(weatherDto)).thenReturn(weatherDbModel)
            repository.addNewCityInList("Москва")
            verify(dao, times(1)).insertWeather(weatherDbModel)
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT mapper map dto to db model`() {
        runBlocking {
            val weatherDto: WeatherDto = mock()
            whenever(api.getWeather("Москва")).thenReturn(weatherDto)
            repository.addNewCityInList("Москва")
            verify(mapper, times(1)).mapDtoToDbModel(weatherDto)
        }
    }

    @Test
    fun `WHEN get weather list live data EXPECT dao get weather list live data`() {
        runBlocking {
            repository.getWeatherListLiveData()
            verify(dao, times(1)).getWeatherListLiveData()
        }
    }

    @Test
    fun `WHEN delete city from list EXPECT dao delete weather`() {
        runBlocking {
            repository.deleteCityFromList(12)
            verify(dao, times(1)).deleteWeather(12)
        }
    }
}