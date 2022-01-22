package corp.umbrella.weather.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import corp.umbrella.weather.domain.entities.Weather

class WeatherItemDiffCallback: DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.cityId == newItem.cityId
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}