package com.example.photovoltaics.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculateResultModel(
    val gmt: Int,

    val longitude: Double,

    val latitude: Double,

    val collectorTilt: Int,

    val azimuthCollector: Int,

    val roofLength: Int,

    val roofWidth: Int,

    val powerPln: Int,

    val installedCapacityMax: Int,

    val installedCapacity: Double,

    val inverter: Double,

    val pvPrice: String,

    val inverterPrice: String,

    val mounting: String,

    val etc: String,

    val totalPrice: String,

    val energy: Double,

    val income: String,

    val date: String
) : Parcelable
