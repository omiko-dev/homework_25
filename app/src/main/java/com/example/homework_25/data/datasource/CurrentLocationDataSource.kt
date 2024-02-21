package com.example.homework_25.data.datasource

import com.example.homework_25.data.model.LocationDto
import kotlinx.coroutines.flow.MutableStateFlow

interface CurrentLocationDataSource {
     val locationStateFlow: MutableStateFlow<LocationDto>
     suspend fun updateCurrentLocation()
}