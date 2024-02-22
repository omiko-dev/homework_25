package com.example.homework_25.data.datasource

import com.example.homework_25.data.model.LocationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CurrentLocationDataSource {
     val currentLocationStateFlow: StateFlow<LocationDto>
     suspend fun updateCurrentLocation(): Flow<LocationDto>
}