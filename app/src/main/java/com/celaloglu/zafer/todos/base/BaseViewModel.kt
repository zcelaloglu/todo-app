package com.celaloglu.zafer.todos.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celaloglu.zafer.todos.ui.model.ViewState

open class BaseViewModel : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState

    protected var _viewState = MutableLiveData<ViewState>()
}