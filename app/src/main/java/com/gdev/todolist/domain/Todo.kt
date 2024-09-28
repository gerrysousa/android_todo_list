package com.gdev.todolist.domain

data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)


//Fake objects

val todo1 = Todo(1, "Todo 1", "Description 1", false)
val todo2 = Todo(2, "Todo 2", "Description 2", true)
val todo3 = Todo(3, "Todo 3", "Description 3", false)
