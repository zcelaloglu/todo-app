package com.celaloglu.zafer.todos.ui.createupdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.celaloglu.zafer.todos.R
import com.celaloglu.zafer.todos.base.BaseViewModel
import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.ui.createupdate.model.CreateUpdateModel
import com.celaloglu.zafer.todos.ui.model.*
import com.celaloglu.zafer.todos.usecase.*
import com.celaloglu.zafer.todos.util.TodoItemBuilder
import com.celaloglu.zafer.todos.util.validator.InputValidator
import com.celaloglu.zafer.todos.util.validator.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateUpdateViewModel(private val todoUseCase: TodoUseCase,
                            private val tagsUseCase: TagsUseCase)
    : BaseViewModel() {

    val actionType: LiveData<ActionType>
        get() = _actionType

    private var _actionType = MutableLiveData<ActionType>()

    private var id: Int? = null

    fun setItemId(id: Int?) {
        this.id = id
    }

    fun onSaveClick(uiModel: CreateUpdateModel) {

        val titleResult = InputValidator.isValid(uiModel.title)
        if (titleResult != ValidationResult.VALID) {
            _actionType.value = Warning(R.string.warning_missing_title_value)
            return
        }

        val descriptionResult = InputValidator.isValid(uiModel.description)
        if (descriptionResult != ValidationResult.VALID) {
            _actionType.value = Warning(R.string.warning_missing_description_value)
            return
        }

        val dueDateResult = InputValidator.isValid(uiModel.dueDate)
        if (dueDateResult != ValidationResult.VALID) {
            _actionType.value = Warning(R.string.warning_missing_due_date_value)
            return
        }

        val todo = TodoItemBuilder.Builder()
                .id(uiModel.item?.todoId)
                .title(uiModel.title)
                .description(uiModel.description)
                .dueDate(uiModel.dueDate)
                .latitude(uiModel.latitude)
                .longitude(uiModel.longitude)
                .completed(uiModel.completed)
                .build()

        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                if (uiModel.item != null) {
                    todoUseCase.updateTodo(todo)
                    withContext(Dispatchers.Main) {
                        _actionType.value = SaveTags
                    }
                    setAlarmAndFinishActivity(uiModel)
                } else {
                    todoUseCase.insertTodo(todo)
                    withContext(Dispatchers.Main) {
                        _actionType.value = SaveTags
                    }
                    setAlarmAndFinishActivity(uiModel)
                }
            }
        }
    }

    fun onDeleteItemClick(item: ToDoItem?) {
        viewModelScope.launch {
            todoUseCase.deleteTodo(item?.todoId)
            _actionType.value = CancelAlarm(item)
        }
    }

    private fun setAlarm(alarmTime: Long, item: ToDoItem?) {
        viewModelScope.launch {
            if (id == null) {
                val todos = todoUseCase.getTodo().first()
                val newAddedItem = todos[todos.size - 1]
                _actionType.value = SetAlarm(alarmTime, newAddedItem)
            } else {
                _actionType.value = SetAlarm(alarmTime, item)
            }
        }
    }

    fun insertTag(todoId: Int?, text: String?) {
        viewModelScope.launch {
            if (todoId != null) {
                val tagItem = TagItem(tagId = null, parentTodoId = todoId, title = text)
                tagsUseCase.insertTag(tagItem)
            } else {
                todoUseCase.getTodo().collect {
                    val todoItem = it.last()
                    val tagItem = TagItem(tagId = null, parentTodoId = todoItem.todoId, title = text)
                    tagsUseCase.insertTag(tagItem)
                }
            }
        }
    }

    fun getTags(todoId: Int?) {
        viewModelScope.launch {
            tagsUseCase.getTags(todoId).collect {
                _actionType.value = GetTags(it)
            }
        }
    }

    private suspend fun setAlarmAndFinishActivity(uiModel: CreateUpdateModel) {
        if (uiModel.item != null) {
            if (!uiModel.item?.completed!! && uiModel.completed) {
                _actionType.value = CancelAlarm(uiModel.item)
            } else if (uiModel.item?.completed!! && !uiModel.completed) {
                setAlarm(uiModel.alarmTime, uiModel.item)
            } else {
                withContext(Dispatchers.Main) {
                    _actionType.value = CancelAlarm(uiModel.item)
                    _actionType.value = FinishActivity
                }
                setAlarm(uiModel.alarmTime, uiModel.item)
            }
        } else {
            if (!uiModel.completed) {
                setAlarm(uiModel.alarmTime, uiModel.item)
            }
            withContext(Dispatchers.Main) {
                _actionType.value = FinishActivity
            }
        }
    }
}