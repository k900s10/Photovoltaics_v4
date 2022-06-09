package com.example.photovoltaics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.photovoltaics.databinding.ActivityResultBinding
import com.example.photovoltaics.model.CalculateResultModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dropdownBtn = binding.resultDetailHeader
        val dropdownContent = binding.constrainLayoutOfResultDetail

        val resultToolbar = binding.toolbar.toolbar
        val title = binding.toolbar.title

        val result = intent.getParcelableExtra<CalculateResultModel>(CalculateActivity.RESULT)

        //summary
        val date = binding.summaryDateCreated
        val summaryInstalledCapacity = binding.summaryInstalledCapacityValue
        val summaryEnergy = binding.summaryEnergyValue
        val summaryIncome = binding.summaryIncomeValue

        //initial data
        val initialDataLayout = binding.resultDetailInitialData
        val initialDataHeader = initialDataLayout.header
        val gmtTitle = initialDataLayout.titleA1
        val gmtValue = initialDataLayout.answerA1
        val longitudeTitle = initialDataLayout.titleA2
        val longitudeValue = initialDataLayout.answerA2
        val latitudeTitle = initialDataLayout.titleA3
        val latitudeValue = initialDataLayout.answerA3
        val collectorTiltTitle = initialDataLayout.titleA4
        val collectorTiltValue = initialDataLayout.answerA4
        val azimuthCollectorTitle = initialDataLayout.titleA5
        val azimuthCollectorValue = initialDataLayout.answerA5
        val roofLengthTitle = initialDataLayout.titleA6
        val roofLengthValue = initialDataLayout.answerA6
        val roofWidthTitle = initialDataLayout.titleA7
        val roofWidthValue = initialDataLayout.answerA7
        val plnTitle = initialDataLayout.titleA8
        val plnValue = initialDataLayout.answerA8

        //installed capacity
        val installedCapacityLayout = binding.resultDetailInstalledCapacity
        val installedCapacityHeader = installedCapacityLayout.header
        val installedCapacityTitle = installedCapacityLayout.titleA1
        val installedCapacityValue = installedCapacityLayout.answerA1
        val installedCapacityMaxTitle = installedCapacityLayout.titleA2
        val installedCapacityMaxValue = installedCapacityLayout.answerA2
        val inverterTitle = installedCapacityLayout.titleA3
        val inverterValue = installedCapacityLayout.answerA3

        //investment price
        val investmentPriceLayout = binding.resultDetailInvesmentPrice
        val investmentPriceHeader = investmentPriceLayout.header
        val pvPriceTitle = investmentPriceLayout.titleA1
        val pvPriceValue = investmentPriceLayout.answerA1
        val inverterPriceTitle = investmentPriceLayout.titleA2
        val inverterPriceValue = investmentPriceLayout.answerA2
        val mountingPriceTitle = investmentPriceLayout.titleA3
        val mountingPriceValue = investmentPriceLayout.answerA3
        val otherTitle = investmentPriceLayout.titleA4
        val otherValue = investmentPriceLayout.answerA4
        val totalPriceTitle = investmentPriceLayout.titleA5
        val totalPriceValue = investmentPriceLayout.answerA5

        //income
        val incomeLayout = binding.resultDetailIncome
        val incomeHeader = incomeLayout.header
        val energyTitle = incomeLayout.titleA1
        val energyValue = incomeLayout.answerA1
        val incomeTitle = incomeLayout.titleA2
        val incomeValue = incomeLayout.answerA2
        val incomeUnusedTitle = incomeLayout.titleA3
        val incomeUnusedValue = incomeLayout.answerA3

        dropdown(dropdownBtn, constrainLayout = dropdownContent)

        resultToolbar.menu.findItem(R.id.result_btn_share).isVisible = false

        title.text = getString(R.string.result_title)

        resultToolbar.navigationIcon =
            AppCompatResources.getDrawable(applicationContext, R.drawable.ic_ab_arrow_back)
        resultToolbar.setNavigationOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


        resultToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.result_btn_help -> {
                    redirect(applicationContext, HelpCenterActivity::class.java)
                }
            }
            return@setOnMenuItemClickListener false
        }

        //summary
        date.text = result?.date

        summaryInstalledCapacity.text =
            getString(R.string.msg_watt_peak_double, result?.installedCapacity)
        summaryEnergy.text = getString(R.string.msg_energy, result?.energy)
        summaryIncome.text = getString(R.string.msg_price, result?.income)

        //initial data
        initialDataHeader.text = getString(R.string.title_initial_data)

        gmtTitle.text = getString(R.string.initial_data_title_gmt)
        gmtValue.text = result?.gmt.toString()

        longitudeTitle.text = getString(R.string.initial_data_title_longtitude)
        longitudeValue.text = getString(R.string.msg_coordinate_double, result?.longitude)

        latitudeTitle.text = getString(R.string.initial_data_title_latitude)
        latitudeValue.text = getString(R.string.msg_coordinate_double, result?.latitude)

        collectorTiltTitle.text = getString(R.string.initial_data_title_collector_tilt)
        collectorTiltValue.text = getString(R.string.msg_coordinate_int, result?.collectorTilt)

        azimuthCollectorTitle.text = getString(R.string.initial_data_title_azimuth_collector)
        azimuthCollectorValue.text =
            getString(R.string.msg_coordinate_int, result?.azimuthCollector)

        roofLengthTitle.text = getString(R.string.title_roof_length)
        roofLengthValue.text = getString(R.string.msg_meter, result?.roofLength)

        roofWidthTitle.text = getString(R.string.title_roof_width)
        roofWidthValue.text = getString(R.string.msg_meter, result?.roofWidth)

        plnTitle.text = getString(R.string.title_pln)
        plnValue.text = getString(R.string.msg_volt_amper_int, result?.powerPln)

        //installed Capacity
        installedCapacityHeader.text = getString(R.string.title_installed_capacity)

        installedCapacityTitle.text = getString(R.string.title_installed_capacity)
        installedCapacityValue.text =
            getString(R.string.msg_watt_peak_double, result?.installedCapacity)

        inverterTitle.text = getString(R.string.installed_capacity_title_inverter)
        inverterValue.text = getString(R.string.msg_volt_amper_double, result?.inverter)

        installedCapacityMaxTitle.text =
            getString(R.string.installed_capacity_title_installed_capacity_max)
        installedCapacityMaxValue.text =
            getString(R.string.msg_watt_peak_int, result?.installedCapacityMax)


        //investment price
        investmentPriceHeader.text = getString(R.string.title_investment_price)

        pvPriceTitle.text = getString(R.string.investment_price_title_pv_price)
        pvPriceValue.text = getString(R.string.msg_price, result?.pvPrice)

        inverterPriceTitle.text = getString(R.string.investment_price_title_inverter_price)
        inverterPriceValue.text = getString(R.string.msg_price, result?.inverterPrice)

        mountingPriceTitle.text = getString(R.string.investment_price_title_mounting_price)
        mountingPriceValue.text = getString(R.string.msg_price, result?.mounting)

        otherTitle.text = getString(R.string.investment_price_title_other_price)
        otherValue.text = getString(R.string.msg_price, result?.etc)

        totalPriceTitle.text = getString(R.string.investment_price_title_total_price)
        totalPriceValue.text = getString(R.string.msg_price, result?.totalPrice)

        //income
        incomeHeader.text = getString(R.string.title_income)

        energyTitle.text = getString(R.string.income_title_energy)
        energyValue.text = getString(R.string.msg_energy, result?.energy)

        incomeTitle.text = getString(R.string.title_income)
        incomeValue.text = getString(R.string.msg_price, result?.income)

        incomeUnusedTitle.visibility = View.GONE
        incomeUnusedValue.visibility = View.GONE
    }
}