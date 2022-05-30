package com.example.photovoltaics.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photovoltaics.repository.CalculateRepository


@Suppress("UNCHECKED_CAST")
class CalculateViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculateViewModel::class.java)) {
            return CalculateViewModel(
                repository = CalculateRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}