package com.example.homework_25.presentation.screen.map.event

sealed class MapsEvent {
    data object GetCurrentLocation: MapsEvent()
}