package com.example.homework_25.presentation.model

import com.google.android.gms.maps.model.LatLng

data class Place(
    val name: String,
    val latLng: LatLng,
    val address: LatLng,
    val rating: Float
)