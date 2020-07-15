package com.celaloglu.zafer.todos.ui.list

import androidx.lifecycle.viewModelScope
import com.celaloglu.zafer.todos.base.BaseViewModel
import com.celaloglu.zafer.todos.ui.model.Success
import com.celaloglu.zafer.todos.usecase.LocationsUseCase
import com.celaloglu.zafer.todos.usecase.TodoUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoListViewModel(private val todoUseCase: TodoUseCase,
                        private val locationsUseCase: LocationsUseCase)
    : BaseViewModel() {

    fun getTodos() {
        viewModelScope.launch {
            todoUseCase.getTodo().collect {
                _viewState.value = Success(it)
            }
        }
    }

    fun getLocations() {
        viewModelScope.launch {
            locationsUseCase().collect {
                _viewState.value = it
            }
        }
    }
}