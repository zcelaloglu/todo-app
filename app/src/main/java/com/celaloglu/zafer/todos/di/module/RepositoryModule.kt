package com.celaloglu.zafer.todos.di.module

import com.celaloglu.zafer.todos.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single<ITodoRepository> { TodoRepository(get()) }

    single<ILocationRepository> { LocationRepository(get()) }

    single<ITagRepository> { TagRepository(get()) }
}