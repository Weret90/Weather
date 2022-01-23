package corp.umbrella.weather.data.repository

import corp.umbrella.weather.data.local.database.WeatherDao
import corp.umbrella.weather.data.mapper.WeatherMapper
import corp.umbrella.weather.data.remote.models.*
import corp.umbrella.weather.data.remote.network.ApiService
import corp.umbrella.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepository
    private lateinit var repositoryWithMockMapper: WeatherRepository
    private lateinit var dao: WeatherDao
    private lateinit var api: ApiService
    private lateinit var mapper: WeatherMapper
    private lateinit var mockMapper: WeatherMapper
    private lateinit var weatherDtoTestYakutsk: WeatherDto
    private lateinit var weatherDtoTestMoscow: WeatherDto
    private lateinit var weatherDtoTestBeijing: WeatherDto

    @Before
    fun setUp() {
        dao = mock()
        api = mock()
        mockMapper = mock()
        mapper = WeatherMapper()
        repository = WeatherRepositoryImpl(dao, api, mapper)
        repositoryWithMockMapper = WeatherRepositoryImpl(dao, api, mockMapper)

        weatherDtoTestYakutsk = WeatherDto(
            cloudsDto = CloudsDto(100),
            timeOfDateUnix = 1642921835,
            cityId = 2013159,
            mainInfoDto = MainInfoDto(
                feelsLike = -26.69,
                humidity = 68,
                pressure = 1025,
                temp = -26.69,
                tempMax = -26.69,
                tempMin = -28.02
            ),
            cityName = "Якутск",
            snowDto = null,
            timezone = 32400,
            visibility = 10000,
            weatherInfoDto = listOf(
                WeatherInfoDto(
                    description = "небольшой снег",
                    icon = "13n",
                    id = 600,
                    main = "Snow"
                )
            ),
            windDto = WindDto(
                direction = 50,
                gust = 0.0,
                speed = 0.89
            )
        )

        weatherDtoTestMoscow = WeatherDto(
            cloudsDto = CloudsDto(88),
            timeOfDateUnix = 1642925354,
            cityId = 524901,
            mainInfoDto = MainInfoDto(
                feelsLike = -4.49,
                humidity = 97,
                pressure = 1026,
                temp = -4.49,
                tempMax = -3.71,
                tempMin = -5.76
            ),
            cityName = "Москва",
            snowDto = null,
            timezone = 10800,
            visibility = 6409,
            weatherInfoDto = listOf(
                WeatherInfoDto(
                    description = "пасмурно",
                    icon = "04d",
                    id = 804,
                    main = "Clouds"
                )
            ),
            windDto = WindDto(
                direction = 82,
                gust = 2.18,
                speed = 1.3
            )
        )

        weatherDtoTestBeijing = WeatherDto(
            cloudsDto = CloudsDto(64),
            timeOfDateUnix = 1642925462,
            cityId = 1816670,
            mainInfoDto = MainInfoDto(
                feelsLike = -0.06,
                humidity = 47,
                pressure = 1029,
                temp = -0.06,
                tempMax = -0.06,
                tempMin = -0.06
            ),
            cityName = "Пекин",
            snowDto = null,
            timezone = 28800,
            visibility = 10000,
            weatherInfoDto = listOf(
                WeatherInfoDto(
                    description = "облачно с прояснениями",
                    icon = "04d",
                    id = 803,
                    main = "Clouds"
                )
            ),
            windDto = WindDto(
                direction = 143,
                gust = 1.02,
                speed = 0.75
            )
        )
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
            whenever(api.getWeather("Москва")).thenReturn(weatherDtoTestMoscow)
            repository.addNewCityInList("Москва")
            verify(api, times(1)).getWeather("Москва")
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT dao insert weather in list`() {
        runBlocking {
            whenever(api.getWeather("Москва")).thenReturn(weatherDtoTestMoscow)
            repository.addNewCityInList("Москва")
            verify(dao, times(1)).insertWeather(any())
        }
    }

    @Test
    fun `WHEN add new city in list EXPECT mapper map dto to db model`() {
        runBlocking {
            whenever(api.getWeather("Москва")).thenReturn(weatherDtoTestMoscow)
            repositoryWithMockMapper.addNewCityInList("Москва")
            verify(mockMapper, times(1)).mapDtoToDbModel(weatherDtoTestMoscow)
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
            val testDatabase = mutableSetOf(
                mapper.mapDtoToDbModel(weatherDtoTestMoscow),
                mapper.mapDtoToDbModel(weatherDtoTestBeijing)
            )
            assertEquals(2, testDatabase.size)
            whenever(api.getWeather("Якутск")).thenReturn(weatherDtoTestYakutsk)
            whenever(dao.insertWeather(any())).then {
                testDatabase.add(mapper.mapDtoToDbModel(weatherDtoTestYakutsk))
            }
            repository.addNewCityInList("Якутск")
            assertEquals(3, testDatabase.size)
        }
    }

    @Test
    fun `WHEN try to add exist city in list EXPECT database size will not change`() {
        runBlocking {
            val testDatabase = mutableSetOf(
                mapper.mapDtoToDbModel(weatherDtoTestMoscow),
                mapper.mapDtoToDbModel(weatherDtoTestBeijing),
                mapper.mapDtoToDbModel(weatherDtoTestYakutsk)
            )
            assertEquals(3, testDatabase.size)
            whenever(api.getWeather("Якутск")).thenReturn(weatherDtoTestYakutsk)
            whenever(dao.insertWeather(any())).then {
                testDatabase.add(mapper.mapDtoToDbModel(weatherDtoTestYakutsk))
            }
            repository.addNewCityInList("Якутск")
            assertEquals(3, testDatabase.size)
        }
    }

    @Test
    fun `WHEN delete city from list EXPECT database size will decrease`() {
        runBlocking {
            val testDatabase = mutableSetOf(
                mapper.mapDtoToDbModel(weatherDtoTestMoscow),
                mapper.mapDtoToDbModel(weatherDtoTestBeijing)
            )
            assertEquals(2, testDatabase.size)
            whenever(dao.deleteWeather(524901)).then {
                testDatabase.removeIf {
                    it.cityId == 524901
                }
            }
            repository.deleteCityFromList(524901)
            assertEquals(1, testDatabase.size)
        }
    }

    @Test
    fun `WHEN try to delete not exist city from list EXPECT database size will not change`() {
        runBlocking {
            val testDatabase = mutableSetOf(
                mapper.mapDtoToDbModel(weatherDtoTestMoscow),
                mapper.mapDtoToDbModel(weatherDtoTestBeijing)
            )
            assertEquals(2, testDatabase.size)
            whenever(dao.deleteWeather(1000)).then {
                testDatabase.removeIf {
                    it.cityId == 1000
                }
            }
            repository.deleteCityFromList(1000)
            assertEquals(2, testDatabase.size)
        }
    }
}