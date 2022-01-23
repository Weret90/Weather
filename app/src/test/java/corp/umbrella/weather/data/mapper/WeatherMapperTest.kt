package corp.umbrella.weather.data.mapper

import corp.umbrella.weather.data.remote.models.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherMapperTest {

    private lateinit var weatherDtoTest: WeatherDto
    private lateinit var mapper: WeatherMapper

    @Before
    fun setUp() {
        mapper = WeatherMapper()
        weatherDtoTest = WeatherDto(
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
    }

    @Test
    fun `WHEN mapDtoToDbModel testDto EXPECT not null weatherDbModel`() {
        val weatherDbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        assertNotNull(weatherDbModel)
    }

    @Test
    fun `WHEN mapDtoToDbModel testDto EXPECT weatherDbModel with cityId 2013159`() {
        val weatherDbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        assertEquals(2013159, weatherDbModel.cityId)
    }

    @Test
    fun `WHEN mapDtoToDbModel testDto EXPECT weatherDbModel with temp -26,69`() {
        val weatherDbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        assertEquals(-26.69, weatherDbModel.temp, 0.001)
    }

    @Test
    fun `WHEN mapDtoToDbModel testDto EXPECT weatherDbModel with correct cityName`() {
        val weatherDbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        assertEquals("Якутск", weatherDbModel.cityName)
    }

    @Test
    fun `WHEN mapDtoToDbModel testDto EXPECT weatherDbModel with correct description`() {
        val weatherDbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        assertEquals("небольшой снег", weatherDbModel.description)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT not null weather domain entity`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertNotNull(weather)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct timeOfDateField`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("23/01/2022 16:10", weather.timeOfDate)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct temperature`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("-26.69 °C", weather.temperature)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct weatherIconUrl`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("https://openweathermap.org/img/wn/13n@2x.png", weather.weatherIconUrl)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct pressure`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("1025 кПа", weather.pressure)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct humidity`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("68 %", weather.humidity)
    }

    @Test
    fun `WHEN mapDbModelToDomainEntity testDto EXPECT correct windSpeed`() {
        val dbModel = mapper.mapDtoToDbModel(weatherDtoTest)
        val weather = mapper.mapDbModelToDomainEntity(dbModel)
        assertEquals("0.89 м/с", weather.windSpeed)
    }
}