package com.example.homework_25.presentation.screen.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_25.data.common.Resource
import com.example.homework_25.domain.usecase.GetCurrentLocationUseCase
import com.example.homework_25.presentation.mapper.toPresenter
import com.example.homework_25.presentation.screen.map.event.MapsEvent
import com.example.homework_25.presentation.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  MapsFragmentViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
): ViewModel() {

    private val _currentLocationStateFlow = MutableStateFlow(LocationState())
    val currentLocationStateFlow get() = _currentLocationStateFlow.asStateFlow()

    fun onEvent(event: MapsEvent){
        when(event){
            is MapsEvent.GetCurrentLocation -> getCurrentLocation()
        }
    }

    private fun getCurrentLocation(){
        viewModelScope.launch {
            getCurrentLocationUseCase().collect { resource ->
                when(resource){
                    is Resource.Success -> {
                        _currentLocationStateFlow.update {
                            Log.i("omiko", resource.success.toString())
                            it.copy(
                                success = resource.success.toPresenter()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _currentLocationStateFlow.update {
                            it.copy(
                                error = resource.error
                            )
                        }
                    }
                    is Resource.Loader -> {
                        _currentLocationStateFlow.update {
                            it.copy(
                                loader = resource.loader
                            )
                        }
                    }
                }
            }
        }
    }
}