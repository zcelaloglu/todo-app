package com.celaloglu.zafer.todos.database

import androidx.room.Embedded
import androidx.room.Relation

data class TodoWithTags(
        @Embedded val todo: ToDoItem,
        @Relation(
                parentColumn = "todoId",
                entityColumn = "parentTodoId"
        )
        val tags: List<TagItem>
)