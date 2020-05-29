package com.celaloglu.zafer.todos.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.celaloglu.zafer.todos.util.Constants
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.TAGS_TABLE_NAME)
@Parcelize
data class TagItem(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "tagId")
                   val tagId: Int? = 0,
                   @ColumnInfo(name = "parentTodoId")
                   val parentTodoId: Int,
                   @ColumnInfo(name = "title")
                   val title: String?) : Parcelable
