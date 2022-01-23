package corp.umbrella.weather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import corp.umbrella.weather.databinding.ItemWeatherBinding
import corp.umbrella.weather.domain.entities.Weather

class WeatherAdapter : ListAdapter<Weather, WeatherViewHolder>(WeatherItemDiffCallback()) {

    var onWeatherClickListener: ((Weather) -> Unit)? = null
    var onWeatherLongClickListener: ((Weather) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
        holder.itemView.setOnClickListener {
            onWeatherClickListener?.invoke(weather)
        }
        holder.itemView.setOnLongClickListener {
            onWeatherLongClickListener?.invoke(weather)
            true
        }
    }
}