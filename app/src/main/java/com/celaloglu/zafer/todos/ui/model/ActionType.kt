package com.celaloglu.zafer.todos.ui.model

import androidx.annotation.StringRes
import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.database.ToDoItem

sealed class ActionType

class Warning(@StringRes val message: Int) : ActionType()
class CancelAlarm(val item: ToDoItem?) : ActionType()
class SetAlarm(val alarmTime: Long, val item: ToDoItem?) : ActionType()
object FinishActivity : ActionType()
object SaveTags : ActionType()
class GetTags(val tags: List<TagItem>) : ActionType()
