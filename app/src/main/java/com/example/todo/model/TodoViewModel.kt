package com.example.todo.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.local.TodoEntity
import com.example.todo.database.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {

        observeTodos()

    }

    //observe Room
    private fun observeTodos() {
        viewModelScope.launch {
            repository.getAllTodos().collect { todos ->
                _uiState.update {
                    it.copy(
                        todos = todos,
                    )
                }
            }
        }
    }

    //open Add bottom sheet
    fun openAddSheet() {

        _uiState.update {

            it.copy(
                isAddSheetOpen = true
            )

        }

    }

    //close add bottom sheet
    fun closeAddSheet() {

        _uiState.update {

            it.copy(
                isAddSheetOpen = false,
                newTitle = "",
                newDescription = ""
            )

        }

    }

    fun onNewTitleChange(
        title: String
    ) {

        _uiState.update {

            it.copy(
                newTitle = title
            )

        }

    }
    fun onNewDescriptionChange(
        description: String
    ) {

        _uiState.update {

            it.copy(
                newDescription = description
            )

        }

    }
    fun onTodoChecked(
        todo: TodoEntity,
        isChecked: Boolean
    ) {

        viewModelScope.launch {

            repository.updateTodo(

                todo.copy(
                    isCompleted = isChecked
                )

            )

        }

    }

    //save todo
    fun saveNewTodo() {

        val state = _uiState.value

        if (state.newTitle.isBlank()) return

        viewModelScope.launch {

            repository.insertTodo(

                TodoEntity(

                    title = state.newTitle,

                    description = state.newDescription

                )

            )

        }

        closeAddSheet()

    }
    //delete todo
    fun deleteTodo(
        todo: TodoEntity?
    ) {

        viewModelScope.launch {

            repository.deleteTodo(todo)

        }

    }

    //open edit sheet
    fun openEditSheet(
        todo: TodoEntity
    ) {

        _uiState.update {

            it.copy(

                editingTodo = todo,

                editTitle = todo.title,

                editDescription = todo.description,

                isEditSheetOpen = true

            )

        }

    }
    fun onEditTitleChange(
        title: String
    ) {

        _uiState.update {

            it.copy(
                editTitle = title
            )

        }

    }
    fun onEditDescriptionChange(
        description: String
    ) {

        _uiState.update {

            it.copy(
                editDescription = description
            )

        }

    }
    fun cancelEdit() {

        _uiState.update {

            it.copy(

                editingTodo = null,

                editTitle = "",

                editDescription = "",

                isEditSheetOpen = false

            )

        }

    }
    //save edit todo
    fun saveEditedTodo() {

        val state = _uiState.value

        val todo = state.editingTodo ?: return

        viewModelScope.launch {

            repository.updateTodo(

                todo.copy(

                    title = state.editTitle,

                    description = state.editDescription

                )

            )

        }

        cancelEdit()

    }
}
