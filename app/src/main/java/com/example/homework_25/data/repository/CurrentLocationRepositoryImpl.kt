package com.example.homework_25.data.repository

import android.util.Log
import com.example.homework_25.data.common.Resource
import com.example.homework_25.data.datasource.CurrentLocationDataSource
import com.example.homework_25.data.mapper.toDomain
import com.example.homework_25.domain.model.LocationModel
import com.example.homework_25.domain.repository.CurrentLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentLocationRepositoryImpl @Inject constructor(
    private val currentLocationDataSource: CurrentLocationDataSource
) : CurrentLocationRepository {
    override suspend fun getCurrentLocation(): Flow<Resource<LocationModel>> = flow {
        try {
            Log.i("omiko", "repo")
            emit(Resource.Loader(loader = true))
            currentLocationDataSource.updateCurrentLocation().collect {
                Log.i("omiko", it.toString())
                emit(Resource.Success(it.toDomain()))
            }
//            currentLocationDataSource.currentLocationStateFlow.collect {
//                Log.i("omiko", it.toString())
//                emit(Resource.Success(it.toDomain()))
//            }
        } catch (e: Exception) {
            emit(Resource.Error(error = e.message ?: ""))
        } finally {
            emit(Resource.Loader(loader = false))
        }
    }
}