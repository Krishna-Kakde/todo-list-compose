package com.example.todo.database.repository

import com.example.todo.database.local.TodoDao
import com.example.todo.database.local.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {

    override fun getAllTodos(): Flow<List<TodoEntity>> {
        return dao.getAllTodos()
    }

    override suspend fun insertTodo(todo: TodoEntity) {
        dao.insertTodo(todo)
    }

    override suspend fun updateTodo(todo: TodoEntity) {
        dao.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity?) {
        dao.deleteTodo(todo?:return)
    }

}