package com.example.photovoltaics.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photovoltaics.repository.CalculateRepository
import com.example.photovoltaics.room.HistoryDatabase
import com.example.photovoltaics.viewModel.CalculateViewModel


@Suppress("UNCHECKED_CAST")
class CalculateViewModelFactory private constructor(
    private val database: HistoryDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculateViewModel::class.java)) {
            return CalculateViewModel(
                repository = CalculateRepository(
                    dao = database.historyDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: CalculateViewModelFactory? = null

        fun getInstance(context: Context): CalculateViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: CalculateViewModelFactory(
                    HistoryDatabase.getInstance(context)
                ).also { instance = it }
            }
    }
}