package com.gdev.todolist.ui.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdev.todolist.data.TodoRepository
import com.gdev.todolist.navigation.AddEditRoute
import com.gdev.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {
    val todos = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.Delete -> {
                delete(id = event.id)
            }

            is ListEvent.CompletedChange -> {
                completedChange(id = event.id, isCompleted = event.isCompleted)
            }

            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(AddEditRoute(event.id)))
                }
            }
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun addEdit(id: Long?) {

    }

    private fun completedChange(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(id, isCompleted)
        }
    }
}