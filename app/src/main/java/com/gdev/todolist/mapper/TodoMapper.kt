package com.gdev.todolist.mapper

import com.gdev.todolist.data.TodoEntity
import com.gdev.todolist.domain.Todo

class TodoMapper {
    fun map(entity: TodoEntity): Todo {
        return Todo(
            id = entity.id,
            title = entity.title,
            description = entity.description ?: "",
            isCompleted = entity.isCompleted
        )
    }

    fun map(entities: List<TodoEntity>): List<Todo> {
        return entities.map { entity -> map(entity) }
    }
}