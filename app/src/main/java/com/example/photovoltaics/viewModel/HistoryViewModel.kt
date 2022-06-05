package com.example.photovoltaics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photovoltaics.room.HistoryDao
import com.example.photovoltaics.room.HistoryEntity

class HistoryViewModel(
    val dao: HistoryDao
): ViewModel() {

    fun getHistory(): LiveData<List<HistoryEntity>> {
        return dao.getHistory()
    }
}