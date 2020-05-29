package com.celaloglu.zafer.todos.usecase

import com.celaloglu.zafer.todos.repository.ILocationRepository
import org.koin.core.KoinComponent

class LocationsUseCase(
        private val locationRepository: ILocationRepository
): KoinComponent {

    suspend operator fun invoke() = locationRepository.getLocations()
}