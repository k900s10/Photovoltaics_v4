package com.example.photovoltaics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.adapter.HistoryAdapter
import com.example.photovoltaics.databinding.ActivityHistoryBinding
import com.example.photovoltaics.room.HistoryDatabase
import com.example.photovoltaics.viewModel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()
        val toolbar = binding.toolbar.toolbar
        val recyclerView = binding.recyclerView

        toolbar.navigationIcon =
            AppCompatResources.getDrawable(applicationContext, R.drawable.ic_ab_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.history_btn_help -> {
                    redirect(applicationContext, HelpCenterActivity::class.java)
                }
            }
            return@setOnMenuItemClickListener true
        }

        viewModel.getHistory().observe(this) {
            val history = it ?: return@observe

            val historyAdapter = HistoryAdapter()
            historyAdapter.submitList(history)
            recyclerView.adapter = historyAdapter
        }
    }

    private fun getViewModel(): HistoryViewModel {
        return HistoryViewModel(
            dao = HistoryDatabase.getInstance(applicationContext).historyDao()
        )
    }
}