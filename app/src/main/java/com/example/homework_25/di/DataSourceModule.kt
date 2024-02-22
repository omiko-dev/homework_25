package com.example.homework_25.di

import android.content.Context
import com.example.homework_25.data.datasource.CurrentLocationDataSource
import com.example.homework_25.data.datasource.CurrentLocationDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCurrentLocationDataSource(@ApplicationContext context: Context): CurrentLocationDataSource =
        CurrentLocationDataSourceImpl(context = context)

}