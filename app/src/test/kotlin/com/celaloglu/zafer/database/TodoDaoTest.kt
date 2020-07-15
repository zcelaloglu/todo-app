package com.celaloglu.zafer.database

import com.celaloglu.zafer.todos.database.ToDoDao
import com.celaloglu.zafer.todos.util.TodoItemGenerator
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.celaloglu.zafer.BaseTest
import com.celaloglu.zafer.todos.database.ToDoItem
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class TodoDaoTest : BaseTest() {

    private lateinit var dao: ToDoDao

    @Before
    override fun setup() {
        super.setup()
        dao = db.toDoDao()
    }

    @Test
    fun `given a todo entity when db insertion performed, getTodos() returns correct count`()
            = runBlockingTest {
        val todo = TodoItemGenerator.generateTodo()
        dao.insert(todo)
        dao.getToDos().collect {
            Truth.assertThat(it.count()).isEqualTo(1)
        }
    }

    @Test
    fun `given a todo entity when db delete operation performed, getTodos() returns correct count`()
            = runBlockingTest {
        val todo = TodoItemGenerator.generateTodo()
        dao.insert(todo)
        dao.deleteItem(1)
        dao.getToDos().collect {
            Truth.assertThat(it.count()).isEqualTo(0)
        }
    }

    @Test
    fun `when db update operation performed, due date variable of updated todo item returns correct string`()
            = runBlockingTest {
        val todo = TodoItemGenerator.generateTodo()
        dao.insert(todo)
        val updatedTodo = TodoItemGenerator.generateUpdatedTodo()
        dao.updateItem(updatedTodo)
        dao.getToDos().collect {
            val dueDate = it[0].dueDate
            Truth.assertThat(dueDate).isEqualTo("30/05/2020")
        }
    }

    @Test
    fun `when db update operation performed, completed variable of updated todo item returns true`()
            = runBlockingTest {
        val todo = TodoItemGenerator.generateTodo()
        dao.insert(todo)
        dao.updateItem(1)
        dao.getToDos().collect {
            Truth.assertThat(it[0].completed).isEqualTo(true)
        }
    }
}