package com.celaloglu.zafer.repository

import com.celaloglu.zafer.BaseTest
import com.celaloglu.zafer.todos.datasource.TodoDataSource
import com.celaloglu.zafer.todos.repository.ITodoRepository
import com.celaloglu.zafer.todos.repository.TodoRepository
import com.celaloglu.zafer.todos.ui.model.ViewState
import com.celaloglu.zafer.todos.util.TodoItemGenerator
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
internal class TodoRepositoryTest : BaseTest() {

    private lateinit var todoRepository: ITodoRepository

    @Before
    override fun setup() {
        super.setup()
        val dataSource = TodoDataSource(db)
        todoRepository = TodoRepository(dataSource)
    }

    @Test
    fun `given todo item when executed item insertion then return todo list is not null`() {
        runBlockingTest {
            val todo = TodoItemGenerator.generateTodo()
            todoRepository.insertTodo(todo)
            val results = todoRepository.getTodo()
            results.collect {
                Truth.assertThat(it).isNotNull()
            }
        }
    }

    @Test
    fun `given todo item when executed item insertion then the type of return value is ViewState`() {
        runBlockingTest {
            val todo = TodoItemGenerator.generateTodo()
            todoRepository.insertTodo(todo)
            val results = todoRepository.getTodo()
            results.collect {
                Truth.assertThat(it).isInstanceOf(ViewState::class.java)
            }
        }
    }
}