package com.gdev.todolist.ui.feature

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdev.todolist.domain.Todo
import com.gdev.todolist.domain.todo1
import com.gdev.todolist.domain.todo2
import com.gdev.todolist.domain.todo3
import com.gdev.todolist.ui.components.TodoItem
import com.gdev.todolist.ui.theme.TodoListTheme

@Composable
fun ListScreen() {
    ListContent(
        todos = listOf(
            todo1, todo2, todo3
        )
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListContent(
    todos: List<Todo>
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onItemClicked = {},
                    onCompletedChange = {},
                    onDeleteClicked = {}
                )
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
            )
        )
    }
}