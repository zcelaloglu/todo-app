package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.datasource.TodoDataSource
import com.celaloglu.zafer.todos.ui.model.ViewState
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dataSource: TodoDataSource) : ITodoRepository {

    override suspend fun getTodo(): Flow<ViewState> {
        return dataSource.getTodos()
    }

    override suspend fun deleteTodo(id: Int) {
        return dataSource.deleteTodo(id)
    }

    override suspend fun insertTodo(item: ToDoItem) {
        return dataSource.insertTodo(item)
    }

    override suspend fun updateTodo(item: ToDoItem) {
        return dataSource.updateTodo(item)
    }

    override suspend fun updateTodo(id: Int) {
        return dataSource.updateTodo(id)
    }
}