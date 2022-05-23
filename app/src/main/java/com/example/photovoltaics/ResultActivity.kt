package com.example.photovoltaics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photovoltaics.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dropdownBtn = binding.resultDetailHeader
        val dropdownContent = binding.constrainLayoutOfResultDetail

        dropdown(dropdownBtn, constrainLayout = dropdownContent)

        val toolbar = binding.includeToolbar.toolbar
        val title = binding.includeToolbar.title

        title.text = getString(R.string.result_title)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.result_btn_help -> {
                    redirect(binding.root.context, HelpCenterActivity::class.java)
                }
            }
            return@setOnMenuItemClickListener false
        }

    }
}