package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import corp.umbrella.weather.databinding.FragmentWeatherDetailBinding
import corp.umbrella.weather.domain.entities.Weather

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private val weather: Weather by lazy {
        requireArguments().getParcelable<Weather>(KEY_WEATHER) as Weather
    }

    companion object {

        private const val KEY_WEATHER = "weather"

        fun newInstance(weather: Weather): WeatherDetailFragment {
            return WeatherDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_WEATHER, weather)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            weatherDetailCityName.text = weather.cityName
            weatherStatus.text = weather.description
            weatherDetailTemp.text = weather.temperature
            Picasso.get().load(weather.weatherIconUrl).into(weatherStatusIcon)
            clouds.text = weather.cloudsPercent
            humidity.text = weather.humidity
            wind.text = weather.windSpeed
            pressure.text = weather.pressure
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}