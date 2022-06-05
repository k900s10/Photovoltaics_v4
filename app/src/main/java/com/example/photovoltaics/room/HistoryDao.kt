package com.example.photovoltaics.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getHistory():LiveData<List<HistoryEntity>>

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 2")
    fun getRecentHistory():LiveData<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveCalculateResult(result: List<HistoryEntity>)

}