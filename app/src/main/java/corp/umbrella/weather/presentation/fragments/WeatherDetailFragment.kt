package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import corp.umbrella.weather.databinding.FragmentWeatherDetailBinding

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WeatherDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = args.weather

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