package com.celaloglu.zafer.todos.ui.model

sealed class ViewState

class Success<T>(val data: T) : ViewState()
class Location<T>(val data: T) : ViewState()