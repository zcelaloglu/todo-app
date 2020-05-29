package com.celaloglu.zafer.todos.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.celaloglu.zafer.todos.util.Constants
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.TODO_TABLE_NAME)
@Parcelize
data class ToDoItem(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "todoId")
                    val todoId: Int? = 0,
                    @ColumnInfo(name = "title")
                    val title: String?,
                    @ColumnInfo(name = "description")
                    val description: String?,
                    @ColumnInfo(name = "dueDate")
                    val dueDate: String?,
                    @ColumnInfo(name = "latitude")
                    val latitude: Double?,
                    @ColumnInfo(name = "longitude")
                    val longitude: Double?,
                    @ColumnInfo(name = "completed")
                    val completed: Boolean) : Parcelable
