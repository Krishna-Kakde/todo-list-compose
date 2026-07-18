package com.example.todo.database.repository

import com.example.todo.database.local.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllTodos(): Flow<List<TodoEntity>>

    suspend fun insertTodo(todo: TodoEntity)

    suspend fun updateTodo(todo: TodoEntity)

    suspend fun deleteTodo(todo: TodoEntity?)
}