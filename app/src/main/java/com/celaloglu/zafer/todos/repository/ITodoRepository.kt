package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.ui.model.ViewState
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {

    suspend fun getTodo(): Flow<ViewState>

    suspend fun deleteTodo(id: Int)

    suspend fun insertTodo(item: ToDoItem)

    suspend fun updateTodo(item: ToDoItem)

    suspend fun updateTodo(id: Int)

}