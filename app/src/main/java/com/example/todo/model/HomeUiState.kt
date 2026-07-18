package com.example.todo.model

import com.example.todo.database.local.TodoEntity

data class HomeUiState(
    // Todos from Room
    val todos: List<TodoEntity> = emptyList(),

    // Add Todo Bottom Sheet
    val newTitle: String = "",
    val newDescription: String = "",

    // Edit Bottom Sheet
    val editingTodo: TodoEntity? = null,
    val editTitle: String = "",
    val editDescription: String = "",

    // UI
    val isAddSheetOpen: Boolean = false,
    val isEditSheetOpen: Boolean = false,
)