package com.gdev.todolist.ui.feature.list

import com.gdev.todolist.domain.Todo

interface ListEvent {
    data class Delete(val id: Long) : ListEvent
    data class CompletedChange(val id: Long, val isCompleted: Boolean) : ListEvent
    data class AddEdit(val id: Long?) : ListEvent
}