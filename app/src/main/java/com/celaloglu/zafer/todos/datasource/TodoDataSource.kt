package com.celaloglu.zafer.todos.datasource

import com.celaloglu.zafer.todos.database.ToDoDatabase
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.ui.model.Success
import com.celaloglu.zafer.todos.ui.model.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class TodoDataSource(private val database: ToDoDatabase) {

    fun getTodos(): Flow<List<ToDoItem>> {
        return database.toDoDao().getToDos()
    }

    suspend fun deleteTodo(id: Int?) {
        database.toDoDao().deleteItem(id)
    }

    suspend fun insertTodo(todo: ToDoItem) {
        database.toDoDao().insert(todo)
    }

    suspend fun updateTodo(item: ToDoItem) {
        database.toDoDao().updateItem(item)
    }

    suspend fun updateTodo(id: Int) {
        database.toDoDao().updateItem(id)
    }
}