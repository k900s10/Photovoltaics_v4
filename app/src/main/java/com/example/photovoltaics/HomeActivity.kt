package com.example.photovoltaics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photovoltaics.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btnCalculate = binding.quickAction.btnCalculate
        val btnHelpCenter = binding.quickAction.btnHelpCenter
        val btnHistory = binding.button

        btnCalculate.setOnClickListener {
            redirect(applicationContext, CalculateActivity::class.java)
        }

        btnHelpCenter.setOnClickListener {
            redirect(applicationContext, HelpCenterActivity::class.java)
        }

        btnHistory.setOnClickListener {
            redirect(applicationContext, HistoryActivity::class.java)
        }
    }
}