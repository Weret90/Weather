package corp.umbrella.weather.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import corp.umbrella.weather.databinding.ItemWeatherBinding
import corp.umbrella.weather.domain.entities.Weather

class WeatherViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: Weather) {
        with(binding) {
            cityName.text = weather.cityName
            dateTime.text = weather.timeOfDate
            temperature.text = weather.temperature
            Picasso.get().load(weather.weatherIconUrl).into(weatherStatusIcon)
        }
    }
}