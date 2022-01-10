package corp.umbrella.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import corp.umbrella.weather.databinding.FragmentAddNewCityBinding
import corp.umbrella.weather.domain.utils.Result
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

        viewModel.addNewCityResultLiveDate.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result) {
                    is Result.Success -> {
                        parentFragmentManager.popBackStack()
                    }
                    is Result.Error -> {
                        showToast("Не получилось добавить город: ${result.error.message}")
                    }
                }
                viewModel.clearLiveData()
            }
        }

        viewModel.incorrectCityNameLiveData.observe(viewLifecycleOwner) {
            it?.let {
                showToast("Некорректное или пустое название города. Только русские буквы без пробелов, минимум 3 буквы")
                viewModel.clearLiveData()
            }
        }

        viewModel.loadingBarLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.buttonInsertNewCity.isEnabled = false
                } else {
                    binding.loadingBar.visibility = View.GONE
                    binding.buttonInsertNewCity.isEnabled = true
                }
                viewModel.clearLiveData()
            }
        }

        binding.buttonInsertNewCity.setOnClickListener {
            val cityName = binding.cityNameInputField.text.toString()
            viewModel.addNewCity(cityName)
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