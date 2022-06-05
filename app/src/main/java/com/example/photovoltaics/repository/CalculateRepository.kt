package com.example.photovoltaics.repository

import android.util.Log
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.model.Result
import com.example.photovoltaics.room.HistoryDao
import com.example.photovoltaics.room.HistoryEntity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Suppress("SameParameterValue")
class CalculateRepository(
    val dao: HistoryDao
) {

    suspend fun calculate(length: Int, width: Int, powerPln: Int): Result<CalculateResultModel> {
        return try {

            //Initial Data
            val gmt = 7
//        val lmt = 105
            val longitude = 112.795678
            val latitude = -7.282695
            val collectorTilt = 15
            val azimuthCollector = 180

            //pv output
            val pvOutput = 3071.983

            //pv module
            val lengthPer100Wp = 0.5
            val widthPer100Wp = 0.25
            val avgPvPrice = 3696933
            val avgInverterPrice = 4423

            //sizing area
//        val areaRoof = length * width
            val sizingLengthRoof: Int = sizingLength1(length, lengthPer100Wp)
            val sizingWidthRoof: Int = sizingWidth1(width, widthPer100Wp)
            val numberOfPvInstalled = numberOfPvInstalled(sizingLengthRoof, sizingWidthRoof)
            val installedCapacityMax = installedCapacityMax(numberOfPvInstalled)
            val installedCapacity = installedCapacity(powerPln)
            val wp1 = wp1(installedCapacity)
            val wp2 = wp2(wp1)
            val inverter = inverter(wp2)
            val lengthFinal = lengthFinal(lengthPer100Wp, wp1)
//        val widthFinal = widthFinal(widthPer100Wp, wp1)
            val numberOfPvInstalledFinal = numberOfPvInstalledFinal(lengthFinal, lengthPer100Wp)

            //investment price
            val pvPrice = pvPrice(wp1, avgPvPrice)
            val inverterPrice = inverterPrice(inverter, avgInverterPrice)
            val mounting = 1232000
            val etc = 3000000
            val totalPrice = totalPrice(pvPrice, inverterPrice, mounting, etc)

            //income
            val energy = energy(pvOutput, numberOfPvInstalledFinal)
            val income = income(powerPln, energy)

            val currentDate = createdDate()

            val result = CalculateResultModel(
                gmt,
                longitude,
                latitude,
                collectorTilt,
                azimuthCollector,
                length,
                width,
                powerPln,
                installedCapacityMax,
                installedCapacity,
                inverter,
                pvPrice.currencyFormat(),
                inverterPrice.currencyFormat(),
                mounting.currencyFormat(),
                etc.currencyFormat(),
                totalPrice.currencyFormat(),
                energy,
                income.currencyFormat(),
                currentDate
            )

            saveCalculateResult(result)

            Result.Success(result)
        } catch (e: Exception) {
            Log.w("Calculate", "Calculate: ${e.message.toString()} ")
            Result.Error(e)
        }
    }

    private suspend fun saveCalculateResult(model: CalculateResultModel) {

        val result = ArrayList<HistoryEntity>()
        val entity = HistoryEntity(
            created = model.date,
            gmt = model.gmt,
            longitude = model.longitude,
            latitude = model.latitude,
            collectorTilt = model.collectorTilt,
            azimuthCollector = model.azimuthCollector,
            roofLength = model.roofLength,
            roofWidth = model.roofWidth,
            powerPln = model.powerPln,
            installedCapacityMax = model.installedCapacityMax,
            installedCapacity = model.installedCapacity,
            inverter = model.inverter,
            pvPrice = model.pvPrice,
            inverterPrice = model.inverterPrice,
            mounting = model.mounting,
            etc = model.etc,
            totalPrice = model.totalPrice,
            energy = model.energy,
            income = model.income
        )
        result.add(entity)

        dao.saveCalculateResult(result)
    }

    private fun createdDate(): String {
        val dateFormat = SimpleDateFormat("dd/M/yyyy HH:mm", Locale.UK)

        val dayName = LocalDate.now().dayOfWeek.name.lowercase()
        val date = dateFormat.format(Date())

        return "$dayName, $date"
    }

    private fun income(powerPln: Int, energy: Double): Double {
        //data PLN
        val r1Tr900 = 1352.00
        val r1Tr1300 = 1444.70

        return if (powerPln != r1Tr900.toInt()) {
            energy * r1Tr1300 * 0.65
        } else {
            energy * r1Tr900 * 0.65
        }
    }

    private fun energy(pvOutput: Double, numberOfPvInstalledFinal: Int): Double {
        val result = pvOutput * numberOfPvInstalledFinal * 100 / 2 / 1000
        return result.doubleFormat()
    }

    private fun totalPrice(pvPrice: Int, inverterPrice: Int, mounting: Int, etc: Int): Int {
        return pvPrice + inverterPrice + mounting + etc
    }

    private fun inverterPrice(inverter: Double, avgInverterPrice: Int): Int {
        return inverter.toInt() * avgInverterPrice
    }

    private fun pvPrice(wp1: Int, avgPvPrice: Int): Int {
        val result: Double = (wp1.toDouble() / 1000) * avgPvPrice.toDouble()

        return result.toInt()
    }

    private fun numberOfPvInstalledFinal(lengthFinal: Int, lengthPer100Wp: Double): Int {
        val result = lengthFinal / lengthPer100Wp
        return result.toInt()
    }

//    private fun widthFinal(widthPer100Wp: Double, wp1: Int): Int {
//        val result = widthPer100Wp / 100 * wp1
//        return result.toInt()
//    }

    private fun lengthFinal(lengthPer100Wp: Double, wp1: Int): Int {
        val result = lengthPer100Wp / 100 * wp1
        return result.toInt()
    }

    private fun inverter(wp2: Double): Double {
        val result = wp2 / 0.85
        return result.doubleFormat()
    }

    private fun wp2(wp1: Int): Double {
        return wp1 * 0.873
    }

    private fun wp1(installedCapacity: Double): Int {
        return ((installedCapacity.toInt().toString().dropLast(2)) + "00").toInt()
    }

    private fun installedCapacity(inverterMax: Int): Double {
        val va = inverterMax * 0.85
        val result = va / 0.873
        return result.doubleFormat()
    }

    private fun sizingWidth1(width: Int, widthPer100Wp: Double): Int {
        val result = (width / widthPer100Wp) * 2
        return result.toInt()
    }

    private fun sizingLength1(length: Int, lengthPer100Wp: Double): Int {
        val result = (length / lengthPer100Wp) * 2
        return result.toInt()
    }

    private fun numberOfPvInstalled(sizingLength1: Int, sizingWidth1: Int): Int {
        return if (sizingLength1 > sizingWidth1) sizingWidth1 else sizingLength1
    }

    private fun installedCapacityMax(numberOfPvInstalled: Int): Int {
        return numberOfPvInstalled * 100
    }

    private fun <T> T.currencyFormat(): String {
        val formatter = DecimalFormat("#,###,###,###,###,###", DecimalFormatSymbols(Locale.UK))

        return formatter.format(this).toString()
    }

    private fun Double.doubleFormat(): Double {
        val formatter = DecimalFormat("################.###")

        return formatter.format(this).toDouble()
    }


}