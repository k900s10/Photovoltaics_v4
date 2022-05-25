package com.example.photovoltaics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
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

        toolbar.navigationIcon =
            AppCompatResources.getDrawable(applicationContext, R.drawable.ic_ab_arrow_back)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.result_btn_help -> {
                    redirect(applicationContext, HelpCenterActivity::class.java)
                }
            }
            return@setOnMenuItemClickListener false
        }

    }
}