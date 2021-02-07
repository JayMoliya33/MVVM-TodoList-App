package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.model.Todo

@Dao
interface TodoDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo : Todo)

    @Update()
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("Delete from todo_table")
    suspend fun deleteAllTodo()

    @Query("Select * from todo_table order by id ASC")
    fun getAllTodo() : LiveData<List<Todo>>
}