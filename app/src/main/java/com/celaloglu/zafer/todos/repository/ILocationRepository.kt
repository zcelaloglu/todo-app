package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.ui.model.ViewState
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {

    suspend fun getLocations(): Flow<ViewState>
}