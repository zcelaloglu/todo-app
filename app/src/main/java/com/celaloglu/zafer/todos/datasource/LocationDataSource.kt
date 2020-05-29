package com.celaloglu.zafer.todos.datasource

import com.celaloglu.zafer.todos.database.ToDoDatabase
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.ui.model.Location
import com.celaloglu.zafer.todos.ui.model.ViewState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationDataSource(private val database: ToDoDatabase) {

    suspend fun getLocations(): Flow<ViewState> = flow{
        val todos = database.toDoDao().getToDos()
        val latlongs = getLocations(todos)
        emit(Location(latlongs))
    }

    private fun getLocations(todos: List<ToDoItem>): ArrayList<LatLng> {
        val latlongs = arrayListOf<LatLng>()
        for (todo in todos) {
            if (todo.latitude != null && todo.longitude != null) {
                val latlong = LatLng(todo.latitude, todo.longitude)
                latlongs.add(latlong)
            }
        }
        return latlongs
    }
}