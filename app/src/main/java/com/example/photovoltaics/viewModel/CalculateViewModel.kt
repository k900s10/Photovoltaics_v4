package com.example.photovoltaics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.model.Result
import com.example.photovoltaics.repository.CalculateRepository
import kotlinx.coroutines.launch

class CalculateViewModel(
    val repository: CalculateRepository
) : ViewModel() {

    private val _calculateResult = MutableLiveData<CalculateResultModel>()
    val calculateResult: LiveData<CalculateResultModel> = _calculateResult

    fun calculate(length: Int, width: Int, powerPln: Int) {

        viewModelScope.launch {
            val result = repository.calculate(length, width, powerPln)

            if (result is Result.Success) {
                val calculation = result.data

                _calculateResult.value = calculation
            }
        }
    }
}