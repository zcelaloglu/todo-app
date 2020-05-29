package com.celaloglu.zafer.todos.ui.createupdate.model

import com.celaloglu.zafer.todos.database.ToDoItem

data class CreateUpdateModel(
        var item: ToDoItem? = null,
        var dueDate: String? = null,
        var latitude: Double? = null,
        var longitude: Double? = null,
        var alarmTime: Long? = null,
        var completed: Boolean? = null,
        var title: String? = null,
        var description: String? = null
)