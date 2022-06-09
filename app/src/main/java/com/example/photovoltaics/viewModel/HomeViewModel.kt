package com.example.photovoltaics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photovoltaics.room.HistoryDao
import com.example.photovoltaics.room.HistoryEntity

class HomeViewModel(
    private val dao: HistoryDao
) : ViewModel() {

    fun getRecentHistory(): LiveData<List<HistoryEntity>> {
        return dao.getHistory()
    }
}