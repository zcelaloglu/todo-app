package com.celaloglu.zafer.todos.di.module

import com.celaloglu.zafer.todos.ui.createupdate.CreateUpdateViewModel
import com.celaloglu.zafer.todos.ui.list.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { TodoListViewModel(get(), get()) }

    viewModel { CreateUpdateViewModel(get(), get()) }

}