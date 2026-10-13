package com.example.todo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todo.database.local.TodoDao
import com.example.todo.database.local.TodoDatabase
import com.example.todo.database.local.TodoDatabase_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
                return  Room.databaseBuilder(
                        context,
                        TodoDatabase::class.java,
                        "todo_database"
                    ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }
}