package com.example.homework_25.presentation.state

import com.example.homework_25.presentation.model.LocationUi

data class LocationState (
    val success: LocationUi? = null,
    val error: String? = null,
    val loader: Boolean = false
)