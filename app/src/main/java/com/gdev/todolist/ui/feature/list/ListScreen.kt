package com.gdev.todolist.ui.feature.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdev.todolist.data.TodoDatabaseProvider
import com.gdev.todolist.data.TodoRepositoryImpl
import com.gdev.todolist.domain.Todo
import com.gdev.todolist.domain.todo1
import com.gdev.todolist.domain.todo2
import com.gdev.todolist.domain.todo3
import com.gdev.todolist.navigation.AddEditRoute
import com.gdev.todolist.ui.UiEvent
import com.gdev.todolist.ui.components.TodoItem
import com.gdev.todolist.ui.feature.addedit.AddEditViewModel
import com.gdev.todolist.ui.theme.TodoListTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    val context = LocalContext.current
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(dao = database.dao)
    val viewModel = viewModel<ListViewModel>() {
        ListViewModel(repository = repository)
    }

    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }

                UiEvent.NavigateBack -> {}
                is UiEvent.ShowSnackbar -> {

                }

                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    ListContent(
        todos = todos, onEvent = viewModel::onEvent
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onEvent(ListEvent.AddEdit(null)) }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(todo = todo,
                    onItemClicked = {
                        onEvent(ListEvent.AddEdit(todo.id))
                    },
                    onCompletedChange = {
                        onEvent(ListEvent.CompletedChange(todo.id, it))
                    },
                    onDeleteClicked = {
                        onEvent(ListEvent.Delete(todo.id))
                    })
                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}

@Preview
@Composable
fun ListScreenPreview() {
    TodoListTheme {
        ListContent(
            todos = listOf(
                todo1, todo2, todo3
            ),
            onEvent = {}
        )
    }
}
