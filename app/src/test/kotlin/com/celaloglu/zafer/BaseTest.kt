package com.celaloglu.zafer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.celaloglu.zafer.todos.database.ToDoDatabase
import com.celaloglu.zafer.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    /**
     * Swaps the background executor used by the Architecture Components with a different one which
     * executes each task synchronously.
     **/
    @get:Rule
    open val instantExecutorRule = InstantTaskExecutorRule()

    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    open val coroutineTestRule = CoroutineTestRule()

    protected lateinit var db: ToDoDatabase

    @Before
    open fun setup() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                ToDoDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}