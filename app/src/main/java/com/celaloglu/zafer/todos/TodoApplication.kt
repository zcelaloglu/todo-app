package com.celaloglu.zafer.todos

import android.app.Application
import com.celaloglu.zafer.todos.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TodoApplication)
            modules(listOf(viewModelsModule,
                    databaseModule,
                    repositoryModule,
                    dataSourceModule,
                    useCaseModule))
        }
    }
}