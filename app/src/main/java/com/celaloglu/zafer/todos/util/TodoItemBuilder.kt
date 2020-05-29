package com.celaloglu.zafer.todos.util

import com.celaloglu.zafer.todos.database.ToDoItem

class TodoItemBuilder {

    data class Builder(var id: Int? = null,
                       var title: String? = null,
                       var description: String? = null,
                       var dueDate: String? = null,
                       var latitude: Double? = null,
                       var longitude: Double? = null,
                       var completed: Boolean? = null) {

        fun id(id: Int?) = apply { this.id = id }
        fun title(title: String?) = apply { this.title = title }
        fun description(description: String?) = apply { this.description = description }
        fun dueDate(dueDate: String?) = apply { this.dueDate = dueDate }
        fun latitude(latitude: Double?) = apply { this.latitude = latitude }
        fun longitude(longitude: Double?) = apply { this.longitude = longitude }
        fun completed(isCompleted: Boolean?) = apply { this.completed = isCompleted }
        fun build() = ToDoItem(id, title, description, dueDate, latitude, longitude, completed!!)
    }
}