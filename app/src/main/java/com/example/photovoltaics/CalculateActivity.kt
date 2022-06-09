package com.example.photovoltaics

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.databinding.ActivityCalculateBinding
import com.example.photovoltaics.viewModel.CalculateViewModel
import com.example.photovoltaics.viewModel.factory.CalculateViewModelFactory

class CalculateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityCalculateBinding
    private var spinnerValue: String = ""
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
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        val choice = parent?.getItemAtPosition(pos).toString()

        if (choice != "Power limit") {
            spinnerValue = choice
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun getViewModel(): CalculateViewModel {
        val factory = CalculateViewModelFactory.getInstance(applicationContext)
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

        val roofLengthInput = binding.cardView1.formLengthValue
        val roofWidthInput = binding.cardView1.formWidthValue

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
            roofLengthInput.text?.clear()
            roofWidthInput.text?.clear()
            plnSpinner.setSelection(0)
        }

        plnSpinner.onItemSelectedListener = this

        btnCalculate.setOnClickListener {
            val roofLengthValue = roofLengthInput.text.toString()
            val roofWidthValue = roofWidthInput.text.toString()

            viewModel.calculateDataChanged(
                width = roofWidthValue,
                length = roofLengthValue,
                powerPln = spinnerValue
            )

            viewModel.calculateForm.observe(this) {
                val result = it ?: return@observe

                if (result.lengthError != null)
                    roofLengthInput.error = getString(result.lengthError)
                if (result.widthError != null)
                    roofWidthInput.error = getString(result.widthError)
                if (result.plnPowerError != null)
                    Toast.makeText(
                        applicationContext,
                        getString(result.plnPowerError),
                        Toast.LENGTH_SHORT
                    ).show()

                if (result.isDataValid)
                    if (roofLengthValue.isNotBlank() && roofWidthValue.isNotBlank())
                        viewModel.calculate(
                            length = roofLengthValue.toInt(),
                            width = roofWidthValue.toInt(),
                            spinnerValue.toInt()
                        )
            }
        }

    }


    companion object {
        const val RESULT = "CALCULATE_RESULT"
    }

}