package com.example.photovoltaics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.repository.CalculateRepository

class CalculateViewModel(
    val repository: CalculateRepository
) : ViewModel() {

    private val _calculateResult = MutableLiveData<CalculateResultModel>()
    val calculateResult: LiveData<CalculateResultModel> = _calculateResult

    fun calculate(length: Int, width: Int, powerPln: Int) {
        val result = repository.calculate(length, width, powerPln)

        _calculateResult.value = result
    }
}