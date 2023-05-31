package com.fazlerabbikhan.hishabeetodo.repository
import androidx.lifecycle.LiveData
import com.fazlerabbikhan.hishabeetodo.database.TodoDao
import com.fazlerabbikhan.hishabeetodo.model.Todo

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllTodos(): LiveData<List<Todo>> {
        return todoDao.getAllTodos()
    }

    suspend fun addTask(task: String) {
        val todo = Todo(0, task)
        todoDao.insert(todo)
    }

    suspend fun updateTask(todo: Todo) {
        todoDao.update(todo)
    }

    suspend fun removeTask(todo: Todo) {
        todoDao.delete(todo)
    }
}