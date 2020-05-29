package com.celaloglu.zafer.todos.di.module

import com.celaloglu.zafer.todos.usecase.*
import org.koin.dsl.module

val useCaseModule = module {

    single { TodoUseCase(get()) }

    single { LocationsUseCase(get()) }

    single { TagsUseCase(get()) }

}