package com.example.photovoltaics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photovoltaics.R
import com.example.photovoltaics.model.CalculateFormState
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.model.Result
import com.example.photovoltaics.repository.CalculateRepository
import kotlinx.coroutines.launch

class CalculateViewModel(
    val repository: CalculateRepository
) : ViewModel() {

    private val _calculateResult = MutableLiveData<CalculateResultModel>()
    val calculateResult: LiveData<CalculateResultModel> = _calculateResult

    private val _calculateForm = MutableLiveData<CalculateFormState>()
    val calculateForm: LiveData<CalculateFormState> = _calculateForm

    fun calculate(length: Int, width: Int, powerPln: Int) {

        viewModelScope.launch {
            val result = repository.calculate(length, width, powerPln)

            if (result is Result.Success) {
                val calculation = result.data

                _calculateResult.value = calculation
            }
        }
    }

    fun calculateDataChanged(width: String, length: String, powerPln: String) {

        if (!isValueValid(length))
            _calculateForm.value = CalculateFormState(lengthError = R.string.invalid_length)
        else if (!isValueValid(width))
            _calculateForm.value = CalculateFormState(widthError = R.string.invalid_width)
        else if (!isValueValid(powerPln))
            _calculateForm.value = CalculateFormState(plnPowerError = R.string.invalid_pln_power)
        else {
            _calculateForm.value = CalculateFormState(isDataValid = true)
        }
    }

    private fun isValueValid(value: String): Boolean {
        if (value.isNotBlank())
            return true
        return false
    }
}