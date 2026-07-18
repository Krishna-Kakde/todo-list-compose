package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.model.TodoViewModel
import com.example.todo.model.TodoViewModelFactory
import com.example.todo.ui.screens.HomeScreen
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TodoViewModel = viewModel(
                factory = TodoViewModelFactory(application)
            )
            ToDoTheme {
                HomeScreen(viewModel)
                }
            }
        }
    }

