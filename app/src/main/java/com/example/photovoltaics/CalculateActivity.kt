package com.example.photovoltaics

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.photovoltaics.databinding.ActivityCalculateBinding

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarDisplay()
        formDisplay()
    }

    private fun toolbarDisplay() {
        val menu = binding.toolbar.toolbar
        val title = binding.toolbar.title
        title.setText(R.string.title_calculate)

        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.history_btn_help -> {
                    redirect(applicationContext, HelpCenterActivity::class.java)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun formDisplay() {
        val btnClear = binding.btnClear

        val roofLengthTitle = binding.cardView1.formTitle1
        val roofLengthInput = binding.cardView1.formInput1
        val roofWidthTitle = binding.cardView1.formTitle2
        val roofWidthInput = binding.cardView1.formInput2

        val plnTitle = binding.cardView2.formTitle1
        val plnInput = binding.cardView2.formInput1
        val input2Title = binding.cardView2.formTitle2
        val input2Input = binding.cardView2.formInput2

        roofLengthTitle.text = getString(R.string.title_roof_length)
        roofLengthInput.hint = getString(R.string.title_roof_length)
        roofWidthTitle.text = getString(R.string.title_roof_width)
        roofWidthInput.hint = getString(R.string.title_roof_width)

        plnTitle.text = getString(R.string.title_pln)
        plnInput.hint = getString(R.string.title_pln)
        input2Title.visibility = View.GONE
        input2Input.visibility = View.GONE

        btnClear.setOnClickListener {
            roofLengthInput.text.clear()
            roofWidthInput.text.clear()
            plnInput.text.clear()
        }
    }
}