package corp.umbrella.weather.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import corp.umbrella.weather.databinding.ItemWeatherBinding
import corp.umbrella.weather.domain.entities.Weather

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private var weatherList: List<Weather> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(weatherList: List<Weather>) {
        this.weatherList = weatherList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}