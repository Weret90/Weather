package corp.umbrella.weather.presentation.utils

import org.junit.Assert.*
import org.junit.Test

class CityNameValidatorTest {

    private val validator = CityNameValidator()

    @Test
    fun `WHEN input correct city name EXPECT true`() {
        assertTrue(validator.isCorrectCityName("Якутск"))
    }

    @Test
    fun `WHEN input incorrect city name with numbers EXPECT false`() {
        assertFalse(validator.isCorrectCityName("Якутск123"))
    }

    @Test
    fun `WHEN input incorrect city name with spaces EXPECT false`() {
        assertFalse(validator.isCorrectCityName("Яку тск 123"))
    }

    @Test
    fun `WHEN input incorrect city name with wrong symbols EXPECT false`() {
        assertFalse(validator.isCorrectCityName("Якутск#"))
    }

    @Test
    fun `WHEN input correct city name with hyphen EXPECT true`() {
        assertTrue(validator.isCorrectCityName("Санкт-Петербург"))
    }

    @Test
    fun `WHEN input incorrect city name with short length EXPECT false`() {
        assertFalse(validator.isCorrectCityName("ая"))
    }

    @Test
    fun `WHEN input incorrect city name with english letters EXPECT false`() {
        assertFalse(validator.isCorrectCityName("London"))
    }
}