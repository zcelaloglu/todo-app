package com.celaloglu.zafer.todos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItem::class, TagItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    abstract fun tagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        ToDoDatabase::class.java,
                        "todos_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}