package com.example.photovoltaics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.photovoltaics.databinding.ActivityHomeBinding
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.room.HistoryDatabase
import com.example.photovoltaics.room.HistoryEntity
import com.example.photovoltaics.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()


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

    override fun onResume() {
        super.onResume()
        historyList()
    }

    private fun historyList() {
        val history1Layout = binding.history1
        val history2Layout = binding.history2

        viewModel.getRecentHistory().observe(this) { history ->

            if (history.isNotEmpty()) {
                Log.i("HistorySize", "History size is: ${history.size}")

                history1Layout.root.visibility = View.VISIBLE

                history1Layout.root.setOnClickListener {
                    onClick(history[0].id, history)
                }

                history1Layout.dateCreated.text = history[0].created

                history1Layout.value1.titleItem.text =
                    getString(R.string.title_installed_capacity)
                history1Layout.value1.valueItem.text =
                    getString(R.string.msg_watt_peak_double, history[0].installedCapacity)

                history1Layout.value2.titleItem.text =
                    getString(R.string.income_title_energy)
                history1Layout.value2.valueItem.text =
                    getString(R.string.msg_energy, history[0].energy)

                history1Layout.value3.titleItem.text =
                    getString(R.string.title_income)
                history1Layout.value3.valueItem.text =
                    getString(R.string.msg_price, history[0].income)

                if (history.size > 1) {
                    history2Layout.root.visibility = View.VISIBLE

                    history2Layout.root.setOnClickListener {
                        onClick(history[1].id, history)
                    }

                    history2Layout.dateCreated.text = history[1].created

                    history2Layout.value1.titleItem.text =
                        getString(R.string.title_installed_capacity)
                    history2Layout.value1.valueItem.text =
                        getString(R.string.msg_watt_peak_double, history[1].installedCapacity)

                    history2Layout.value2.titleItem.text =
                        getString(R.string.income_title_energy)
                    history2Layout.value2.valueItem.text =
                        getString(R.string.msg_energy, history[1].energy)

                    history2Layout.value3.titleItem.text =
                        getString(R.string.title_income)
                    history2Layout.value3.valueItem.text =
                        getString(R.string.msg_price, history[1].income)
                }
            }
        }
    }

    private fun getViewModel(): HomeViewModel {
        val dao = HistoryDatabase.getInstance(applicationContext).historyDao()
        return HomeViewModel(
            dao
        )
    }

    private fun onClick(id: Int, history: List<HistoryEntity>) {
        val index = id - 1

        val model = CalculateResultModel(
            gmt = history[index].gmt,
            longitude = history[index].longitude,
            latitude = history[index].latitude,
            collectorTilt = history[index].collectorTilt,
            azimuthCollector = history[index].azimuthCollector,
            roofLength = history[index].roofLength,
            roofWidth = history[index].roofWidth,
            powerPln = history[index].powerPln,
            installedCapacityMax = history[index].installedCapacityMax,
            installedCapacity = history[index].installedCapacity,
            inverter = history[index].inverter,
            pvPrice = history[index].pvPrice,
            inverterPrice = history[index].inverterPrice,
            mounting = history[index].mounting,
            etc = history[index].etc,
            totalPrice = history[index].totalPrice,
            energy = history[index].energy,
            income = history[index].income,
            date = history[index].created
        )

        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra(CalculateActivity.RESULT, model)
        startActivity(intent)
    }
}