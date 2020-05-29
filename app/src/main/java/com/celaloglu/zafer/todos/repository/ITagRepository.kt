package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.database.TagItem
import kotlinx.coroutines.flow.Flow

interface ITagRepository {

    suspend fun getTags(id: Int): Flow<List<TagItem>>

    suspend fun insertTag(tagItem: TagItem)

}