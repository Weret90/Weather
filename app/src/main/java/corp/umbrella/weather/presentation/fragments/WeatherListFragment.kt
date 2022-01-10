package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import corp.umbrella.weather.R
import corp.umbrella.weather.databinding.FragmentWeatherListBinding
import corp.umbrella.weather.presentation.adapter.WeatherAdapter
import corp.umbrella.weather.presentation.viewmodels.WeatherListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherListFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherListViewModel by viewModel()
    private val weatherAdapter by lazy {
        WeatherAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weatherListRv.adapter = weatherAdapter

        viewModel.getWeatherListLiveData().observe(viewLifecycleOwner) {
            weatherAdapter.setData(it)
        }

        viewModel.loadingBarLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    binding.loadingBar.visibility = View.VISIBLE
                } else {
                    binding.loadingBar.visibility = View.GONE
                }
                viewModel.clearLiveData()
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it?.let {
                showToast("Ошибка загрузки данных: ${it.message}")
                viewModel.clearLiveData()
            }
        }

        binding.buttonAddNewCity.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddNewCityFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}