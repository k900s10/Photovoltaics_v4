package com.example.photovoltaics

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
        val btnFaq1 = binding.faq1.button
        val btnFaq2 = binding.faq2.button
        val btnFaq3 = binding.faq3.button
        val descriptionFaq1 = binding.faq1.description
        val descriptionFaq2 = binding.faq2.description
        val descriptionFaq3 = binding.faq3.description
        val btnForm = binding.formButton

        dropdown(btnFaq1, textView = descriptionFaq1, context = applicationContext)
        dropdown(btnFaq2, textView = descriptionFaq2, context = applicationContext)
        dropdown(btnFaq3, textView = descriptionFaq3, context = applicationContext)

        //modify toolbar
        val toolbar = binding.toolbar.toolbar
        val title = binding.toolbar.title

        title.text = getString(R.string.title_help_center)
        toolbar.navigationIcon =
            AppCompatResources.getDrawable(applicationContext, R.drawable.ic_ab_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }


        val menu = toolbar.menu
        val btnShare = menu.findItem(R.id.result_btn_share)
        val btnHelp = menu.findItem(R.id.result_btn_help)
        btnShare.isVisible = false
        btnHelp.isVisible = false


        btnForm.setOnClickListener {
            val contactUsSubject = binding.formSubject.text.toString()
            val contactUsDescription = binding.formDescription.text.toString()

            composeEmail(contactUsSubject, contactUsDescription)
        }

        btnFaq1.text = getString(R.string.title_what_is_photovoltaics)
        btnFaq2.text = getString(R.string.title_why_doing_feasibility_test)
        btnFaq3.text = getString(R.string.title_what_is_the_requirement_for_feasibility_test)
        descriptionFaq1.text = getString(R.string.msg_what_is_photovoltaics)
        descriptionFaq2.text = getString(R.string.msg_why_doing_feasibility_test)
        descriptionFaq3.text = getString(R.string.msg_what_is_the_requirement_for_feasibility_test)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun composeEmail(subject: String, description: String) {
        val addresses = arrayOf("null@null.null")

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, description)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}