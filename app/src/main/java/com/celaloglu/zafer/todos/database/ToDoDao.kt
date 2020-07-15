package com.celaloglu.zafer.todos.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * from todo")
    fun getToDos(): Flow<List<ToDoItem>>

    @Transaction
    @Query("SELECT * from todo where todoId = :id")
    suspend fun getToDoWithTags(id: Int?): TodoWithTags

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoItem: ToDoItem)

    @Query("DELETE FROM todo WHERE todoId = :id")
    suspend fun deleteItem(id: Int?)

    @Update
    suspend fun updateItem(todo: ToDoItem)

    @Query("UPDATE todo SET completed=1 WHERE todoId = :id")
    suspend fun updateItem(id: Int)
}