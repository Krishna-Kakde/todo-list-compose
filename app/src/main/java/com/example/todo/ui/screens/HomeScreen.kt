package com.example.todo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo.model.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TodoViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,

        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {

                        Text(
                            text = "My Tasks",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${uiState.todos.size - uiState.todos.filter { it.isCompleted }.size} tasks remaining",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {viewModel.openAddSheet()},
                shape = RoundedCornerShape(18.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )
            ) {

                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text("Add Task")
            }
        }

    ) { innerPadding ->

        if (uiState.isAddSheetOpen){
            NewTodoBottomSheet(
                newTitle = uiState.newTitle,
                newDescription = uiState.newDescription,
                onTitleChange = {viewModel.onNewTitleChange(it)},
                onDescriptionChange = {viewModel.onNewDescriptionChange(it)},
                onCancelClicked = {viewModel.closeAddSheet()},
                onSaveClicked = {viewModel.saveNewTodo()},
            )
        }else if (uiState.isEditSheetOpen){
            EditTodoBottomSheet(
                editTitle = uiState.editTitle,
                editDescription = uiState.editDescription,
                onDismissRequest = {viewModel.cancelEdit()},
                onEditTitleChange = {viewModel.onEditTitleChange(it)},
                onEditDescriptionChange = {viewModel.onEditDescriptionChange(it)},
                onEditDeleteClicked = { viewModel.deleteTodo(uiState.editingTodo) },
                onEditSaveClicked = {  viewModel.saveEditedTodo()}
            )
        }else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    // ---------------- Progress Card ----------------

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 6.dp
                        )
                    ) {

                        Column {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                            )

                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        imageVector = Icons.Rounded.Insights,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = "Today's Progress",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                LinearProgressIndicator(
                                    progress = { if (uiState.todos.size == 0) 0f else uiState.todos.filter { it.isCompleted }.size.toFloat() / uiState.todos.size.toFloat()
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "${uiState.todos.filter { it.isCompleted }.size} of ${uiState.todos.size} tasks completed",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ---------------- Section Header ----------------

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Today's Tasks",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        AssistChip(
                            onClick = {},
                            label = {
                                Text("${uiState.todos.size}")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.todos.isEmpty()) {

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        imageVector = Icons.Rounded.TaskAlt,
                                        contentDescription = null,
                                        modifier = Modifier.size(72.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "Nothing to do!",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Tap the button below to create your first task.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                    } else {

                        ElevatedCard(
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(uiState.todos) { todo ->
                                    TodoCard(
                                        todo = todo,
                                        onCheckedChange = { isChecked ->
                                            viewModel.onTodoChecked(todo, isChecked)
                                        },
                                        onEditClick = { viewModel.openEditSheet(todo) },
                                        onDeleteClick = { viewModel.deleteTodo(todo) },
                                        modifier = Modifier
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }