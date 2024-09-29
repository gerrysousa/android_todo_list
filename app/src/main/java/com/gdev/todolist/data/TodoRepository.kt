package com.gdev.todolist.data

import com.gdev.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(title: String, description: String?)

    suspend fun updateCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getBy(id: Long): Todo?
}