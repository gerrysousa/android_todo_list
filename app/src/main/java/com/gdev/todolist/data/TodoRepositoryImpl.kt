package com.gdev.todolist.data

import com.gdev.todolist.domain.Todo
import com.gdev.todolist.mapper.TodoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao,
) : TodoRepository {
    private val mapper = TodoMapper()

    override suspend fun insert(title: String, description: String?) {
        val entity = TodoEntity(
            title = title,
            description = description,
            isCompleted = false,
        )

        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existentEntity = dao.getBy(id) ?: return
        val updatedEntity = existentEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map { entities -> mapper.map(entities) }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id)?.let { entity -> mapper.map(entity) }
    }
}