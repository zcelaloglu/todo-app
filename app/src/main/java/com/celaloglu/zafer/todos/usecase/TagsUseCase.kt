package com.celaloglu.zafer.todos.usecase

import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.repository.ITagRepository
import org.koin.core.KoinComponent

class TagsUseCase(
        private val tagRepository: ITagRepository
): KoinComponent {

    suspend fun getTags(id: Int) = tagRepository.getTags(id)

    suspend fun insertTag(tagItem: TagItem) = tagRepository.insertTag(tagItem)
}