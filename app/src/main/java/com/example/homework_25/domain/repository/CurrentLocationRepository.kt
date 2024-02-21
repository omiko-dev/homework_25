package com.example.homework_25.domain.repository

import com.example.homework_25.data.common.Resource
import com.example.homework_25.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface CurrentLocationRepository {
    suspend fun getCurrentLocation(): Flow<Resource<LocationModel>>
}