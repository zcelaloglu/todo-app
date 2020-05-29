package com.celaloglu.zafer.todos.di.module

import com.celaloglu.zafer.todos.datasource.*
import org.koin.dsl.module

val dataSourceModule = module {

    single { TodoDataSource(get()) }

    single { LocationDataSource(get()) }

    single { TagsDataSource(get()) }
}