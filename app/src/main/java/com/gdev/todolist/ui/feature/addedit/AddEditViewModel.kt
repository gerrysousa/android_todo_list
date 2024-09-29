package com.gdev.todolist.ui.feature.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdev.todolist.data.TodoRepository
import com.gdev.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val repository: TodoRepository,
): ViewModel() {
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditEvent){
        when(event){
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }

            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }
            AddEditEvent.Save -> {
                saveTodo()
            }
        }
    }

    fun saveTodo(){
        viewModelScope.launch {
            if(title.isBlank()){
                _uiEvent.send(UiEvent.ShowSnackbar("The title can't be empty"))
                return@launch
            }
            repository.insert(title, description)
            _uiEvent.send(UiEvent.NavigateBack)
        }
    }
}