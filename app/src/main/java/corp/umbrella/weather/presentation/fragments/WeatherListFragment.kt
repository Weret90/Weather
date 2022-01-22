package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import corp.umbrella.weather.R
import corp.umbrella.weather.databinding.FragmentWeatherListBinding
import corp.umbrella.weather.presentation.adapter.WeatherAdapter
import corp.umbrella.weather.presentation.utils.LoadDataState
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

        viewModel.weatherListLiveData.observe(viewLifecycleOwner) {
            weatherAdapter.setData(it)
        }

        viewModel.loadDataStateLiveData.observe(viewLifecycleOwner) { loadDataState ->
            loadDataState?.let {
                renderLoadDataState(it)
            }
        }

        weatherAdapter.onWeatherClickListener = {
            findNavController().navigate(
                WeatherListFragmentDirections.actionWeatherListFragmentToWeatherDetailFragment(it)
            )
        }

        binding.buttonAddNewCity.setOnClickListener {
            findNavController().navigate(R.id.action_weatherListFragment_to_addNewCityFragment)
        }
    }

    private fun renderLoadDataState(state: LoadDataState) {
        when (state) {
            is LoadDataState.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            is LoadDataState.Success -> {
                binding.loadingBar.visibility = View.GONE
                showToast(getString(R.string.success_update_weather_list))
            }
            is LoadDataState.Error -> {
                binding.loadingBar.visibility = View.GONE
                showToast(
                    String.format(
                        getString(R.string.error_update_weather_list_not_success),
                        state.throwable.message
                    )
                )
            }
        }
        viewModel.clearLiveData()
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}