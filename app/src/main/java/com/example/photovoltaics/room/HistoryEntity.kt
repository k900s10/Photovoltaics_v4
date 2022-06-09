package com.example.photovoltaics.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @field:ColumnInfo(name = "created")
    val created: String,

    @field:ColumnInfo(name = "gmt")
    val gmt: Int,

    @field:ColumnInfo(name = "longitude")
    val longitude: Double,

    @field:ColumnInfo(name = "latitude")
    val latitude: Double,

    @field:ColumnInfo(name = "collectorTilt")
    val collectorTilt: Int,

    @field:ColumnInfo(name = "azimuthCollector")
    val azimuthCollector: Int,

    @field:ColumnInfo(name = "roofLength")
    val roofLength: Int,

    @field:ColumnInfo(name = "roofWidth")
    val roofWidth: Int,

    @field:ColumnInfo(name = "powerPln")
    val powerPln: Int,

    @field:ColumnInfo(name = "installedCapacityMax")
    val installedCapacityMax: Int,

    @field:ColumnInfo(name = "installedCapacity")
    val installedCapacity: Double,

    @field:ColumnInfo(name = "inverter")
    val inverter: Double,

    @field:ColumnInfo(name = "pvPrice")
    val pvPrice: String,

    @field:ColumnInfo(name = "inverterPrice")
    val inverterPrice: String,

    @field:ColumnInfo(name = "mounting")
    val mounting: String,

    @field:ColumnInfo(name = "etc")
    val etc: String,

    @field:ColumnInfo(name = "totalPrice")
    val totalPrice: String,

    @field:ColumnInfo(name = "energy")
    val energy: Double,

    @field:ColumnInfo(name = "income")
    val income: String,
)