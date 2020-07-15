package com.celaloglu.zafer.todos.ui.model

import com.celaloglu.zafer.todos.database.ToDoItem
import com.google.android.gms.maps.model.LatLng

sealed class ViewState

class Success(val data: List<ToDoItem>) : ViewState()
class Location(val data: ArrayList<LatLng>) : ViewState()