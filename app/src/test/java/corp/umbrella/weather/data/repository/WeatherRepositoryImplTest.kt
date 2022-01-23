package corp.umbrella.weather.data.repository

import corp.umbrella.weather.data.local.database.WeatherDao
import corp.umbrella.weather.data.mapper.WeatherMapper
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
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
            repository.addNewCityInList("Москва")
            verify(api, times(1)).getWeather("Москва")
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT dao insert weather in list`() {
        runBlocking {
            repository.addNewCityInList("Москва")
            verify(dao, times(1)).insertWeather(anyOrNull())
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT mapper map dto to db model`() {
        runBlocking {
            repository.addNewCityInList("Москва")
            verify(mapper, times(1)).mapDtoToDbModel(anyOrNull())
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

    @Test
    fun `WHEN add new city in list EXPECT database size will increase`() {
        runBlocking {
            val testDatabase = mutableListOf("someWeather", "someWeather")
            assertEquals(2, testDatabase.size)
            whenever(dao.insertWeather(anyOrNull())).then {
                testDatabase.add("someWeather")
            }
            repository.addNewCityInList("Москва")
            assertEquals(3, testDatabase.size)
        }
    }

    @Test
    fun `WHEN delete city from list EXPECT database size will decrease`() {
        runBlocking {
            val testDatabase = mutableListOf("someWeather", "someWeather")
            assertEquals(2, testDatabase.size)
            whenever(dao.deleteWeather(any())).then {
                testDatabase.removeAt(0)
            }
            repository.deleteCityFromList(12)
            assertEquals(1, testDatabase.size)
        }
    }
}