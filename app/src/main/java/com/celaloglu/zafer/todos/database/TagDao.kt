package com.celaloglu.zafer.todos.database

import androidx.room.*

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tagItem: TagItem)
}