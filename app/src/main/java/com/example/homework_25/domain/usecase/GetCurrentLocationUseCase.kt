package com.example.homework_25.domain.usecase

import com.example.homework_25.domain.repository.CurrentLocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val currentLocationRepository: CurrentLocationRepository
) {
    suspend operator fun invoke() = currentLocationRepository.getCurrentLocation()
}