package com.example.photovoltaics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.databinding.ActivityHelpCenterBinding

class HelpCenterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set up dropdown function
        val btnFaq1 = binding.includeFaq1.button
        val btnFaq2 = binding.includeFaq2.button
        val btnFaq3 = binding.includeFaq3.button
        val descriptionFaq1 = binding.includeFaq1.description
        val descriptionFaq2 = binding.includeFaq2.description
        val descriptionFaq3 = binding.includeFaq3.description

        dropdown(btnFaq1, textView = descriptionFaq1, context = applicationContext)
        dropdown(btnFaq2, textView = descriptionFaq2, context = applicationContext)
        dropdown(btnFaq3, textView = descriptionFaq3, context = applicationContext)

        //modify toolbar
        val toolbar = binding.includeToolbar.toolbar
        val title = binding.includeToolbar.title

        title.text = "Help Center"
        toolbar.navigationIcon =
            AppCompatResources.getDrawable(applicationContext, R.drawable.ic_ab_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }


        val menu = toolbar.menu
        val btnShare = menu.findItem(R.id.result_btn_share)
        val btnHelpCenter = menu.findItem(R.id.result_btn_help)
        btnShare.isVisible = false
        btnHelpCenter.isVisible = false
    }
}