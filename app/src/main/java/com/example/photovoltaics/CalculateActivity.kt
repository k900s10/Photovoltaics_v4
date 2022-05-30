package com.example.photovoltaics

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.databinding.ActivityCalculateBinding
import com.example.photovoltaics.viewModel.CalculateViewModel
import com.example.photovoltaics.viewModel.CalculateViewModelFactory

class CalculateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityCalculateBinding
    private var spinnerValue: Int = 0
    private lateinit var viewModel: CalculateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        toolbarDisplay()
        formDisplay()

        viewModel.calculateResult.observe(this) {
            val result = it ?: return@observe

            val intent = Intent(applicationContext, ResultActivity::class.java)
            intent.putExtra(RESULT, result)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        val choice = parent?.getItemAtPosition(pos).toString()

        if (choice != "Power limit") {
            spinnerValue = choice.toInt()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun getViewModel(): CalculateViewModel {
        val factory = CalculateViewModelFactory()
        val viewModel: CalculateViewModel by viewModels {
            factory
        }
        return viewModel
    }

    private fun toolbarDisplay() {
        val toolbar = binding.toolbar.toolbar
        val title = binding.toolbar.title
        title.setText(R.string.title_calculate)

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

    private fun formDisplay() {
        val btnClear = binding.btnClear
        val btnCalculate = binding.btnCalculate

        val roofLengthInput = binding.cardView1.formLengthValue.text
        val roofWidthInput = binding.cardView1.formWidthValue.text

        val plnSpinner = binding.cardView2.spinner

        ArrayAdapter.createFromResource(
            applicationContext,
            R.array.power_limit,
            simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(simple_spinner_dropdown_item)

            plnSpinner.adapter = adapter
        }


        btnClear.setOnClickListener {
            roofLengthInput.clear()
            roofWidthInput.clear()
        }

        plnSpinner.onItemSelectedListener = this

        btnCalculate.setOnClickListener {

            val roofLengthValue = roofLengthInput.toString().toInt()
            val roofWidthValue = roofWidthInput.toString().toInt()


            viewModel.calculate(length = roofLengthValue, width = roofWidthValue, spinnerValue)
        }

    }

    companion object {
        const val RESULT = "CALCULATE_RESULT"
    }

}