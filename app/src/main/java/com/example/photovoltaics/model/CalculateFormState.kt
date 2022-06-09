package com.example.photovoltaics.model

data class CalculateFormState(
    val widthError: Int? = null,
    val lengthError: Int? = null,
    val plnPowerError: Int? = null,
    val isDataValid: Boolean = false
)
