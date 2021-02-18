package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.model.Todo

class TodoRepository(val db : TodoDatabase) {

    suspend fun insertTodo(todo : Todo){
        db.getTodoDao().insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        db.getTodoDao().updateTodo(todo)
    }

    suspend fun deleteAllTodo(){
        db.getTodoDao().deleteAllTodo()
    }

    suspend fun deleteTodo(todo: Todo){
        db.getTodoDao().deleteTodo(todo)
    }

    fun getAllTodo() : LiveData<List<Todo>>{
        return db.getTodoDao().getAllTodo()
    }
}
