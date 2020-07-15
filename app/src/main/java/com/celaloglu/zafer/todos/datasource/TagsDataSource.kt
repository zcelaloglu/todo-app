package com.celaloglu.zafer.todos.datasource

import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.database.ToDoDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagsDataSource(private val database: ToDoDatabase) {

    suspend fun getTags(id: Int?): Flow<List<TagItem>> = flow{
        val todos = database.toDoDao().getToDoWithTags(id).tags
        emit(todos)
    }

    suspend fun insertTag(tagItem: TagItem) {
        database.tagDao().insert(tagItem)
    }
}