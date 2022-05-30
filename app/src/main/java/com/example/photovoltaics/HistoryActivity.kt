package com.example.photovoltaics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar.toolbar

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
    }
}