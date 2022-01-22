package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import corp.umbrella.weather.R
import corp.umbrella.weather.databinding.FragmentAddNewCityBinding
import corp.umbrella.weather.presentation.utils.LoadDataState
import corp.umbrella.weather.presentation.viewmodels.AddNewCityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewCityFragment : Fragment() {

    private var _binding: FragmentAddNewCityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNewCityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddNewCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadDataStateLiveData.observe(viewLifecycleOwner) { loadDataState ->
            loadDataState?.let {
                renderLoadDataState(it)
            }
        }

        viewModel.incorrectCityNameLiveData.observe(viewLifecycleOwner) {
            it?.let {
                showToast(getString(R.string.error_incorrect_city_name))
                viewModel.clearLiveData()
            }
        }

        binding.buttonInsertNewCity.setOnClickListener {
            val cityName = binding.cityNameInputField.text.toString()
            viewModel.addNewCityInList(cityName)
        }
    }

    private fun renderLoadDataState(state: LoadDataState) {
        when (state) {
            is LoadDataState.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.buttonInsertNewCity.isEnabled = false
            }
            is LoadDataState.Success -> {
                parentFragmentManager.popBackStack()
            }
            is LoadDataState.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.buttonInsertNewCity.isEnabled = true
                showToast(
                    String.format(
                        getString(R.string.error_add_city_in_list_not_success),
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