package com.celaloglu.zafer.todos.di.module

import com.celaloglu.zafer.todos.database.ToDoDatabase
import org.koin.dsl.module

val databaseModule = module {

    single { ToDoDatabase.getDatabase(get()) }

}