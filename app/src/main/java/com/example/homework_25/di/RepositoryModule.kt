package com.example.homework_25.di

import com.example.homework_25.data.datasource.CurrentLocationDataSource
import com.example.homework_25.data.repository.CurrentLocationRepositoryImpl
import com.example.homework_25.domain.repository.CurrentLocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrentLocationRepository(currentLocationDataSource: CurrentLocationDataSource): CurrentLocationRepository =
        CurrentLocationRepositoryImpl(currentLocationDataSource = currentLocationDataSource)

}