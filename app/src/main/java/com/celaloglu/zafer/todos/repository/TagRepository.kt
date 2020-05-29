package com.celaloglu.zafer.todos.repository

import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.datasource.TagsDataSource
import kotlinx.coroutines.flow.Flow

class TagRepository(private val dataSource: TagsDataSource) : ITagRepository {

    override suspend fun getTags(id: Int): Flow<List<TagItem>> {
        return dataSource.getTags(id)
    }

    override suspend fun insertTag(tagItem: TagItem) {
        dataSource.insertTag(tagItem)
    }
}