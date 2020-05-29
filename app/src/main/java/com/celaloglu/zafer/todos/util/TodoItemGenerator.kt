package com.celaloglu.zafer.todos.util

import com.celaloglu.zafer.todos.database.ToDoItem

object TodoItemGenerator {

    fun generateTodos(): List<ToDoItem> {
        val list = arrayListOf<ToDoItem>()
        for (i in 1..2) {
            val item = ToDoItem(null, "Neque porro quisquam est qui dolorem ipsum quia dolor$i", "" +
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below.",
                    "25/05/2020", 41.026, 29.124, false)
            list.add(item)
        }
        return list
    }

    fun generateTodo(): ToDoItem {
        return ToDoItem(1, "Neque porro quisquam est qui dolorem ipsum quia dolor", "" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below.",
                "25/05/2020", 41.026, 29.124, false)
    }

    fun generateUpdatedTodo(): ToDoItem {
        return ToDoItem(1, "Neque porro quisquam est qui dolorem ipsum quia dolor", "" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below.",
                "30/05/2020", 41.026, 29.124, true)
    }
}