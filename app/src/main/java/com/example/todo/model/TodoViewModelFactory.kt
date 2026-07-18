package com.example.todo.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.database.local.TodoDatabase
import com.example.todo.database.repository.TodoRepositoryImpl

class TodoViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {

            val dao = TodoDatabase
                .getDatabase(application)
                .todoDao()

            val repository = TodoRepositoryImpl(dao)

            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}