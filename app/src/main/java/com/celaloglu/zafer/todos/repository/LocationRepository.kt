package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.datasource.LocationDataSource
import com.celaloglu.zafer.todos.ui.model.ViewState
import kotlinx.coroutines.flow.Flow

class LocationRepository(private val dataSource: LocationDataSource) : ILocationRepository {

    override suspend fun getLocations(): Flow<ViewState> {
        return dataSource.getLocations()
    }
}