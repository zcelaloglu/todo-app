package com.celaloglu.zafer.todos.usecase

import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.repository.ITodoRepository
import org.koin.core.KoinComponent

class TodoUseCase(
        private val todoRepository: ITodoRepository
): KoinComponent {

    suspend fun getTodo() = todoRepository.getTodo()

    suspend fun deleteTodo(id: Int?) = todoRepository.deleteTodo(id)

    suspend fun insertTodo(item: ToDoItem) = todoRepository.insertTodo(item)

    suspend fun updateTodo(item: ToDoItem) = todoRepository.updateTodo(item)

    suspend fun updateTodo(id: Int) = todoRepository.updateTodo(id)
}