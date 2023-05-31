package com.fazlerabbikhan.hishabeetodo.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazlerabbikhan.hishabeetodo.model.Todo
import com.fazlerabbikhan.hishabeetodo.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    fun getAllTodos(): LiveData<List<Todo>> {
        return todoRepository.getAllTodos()
    }

    fun addTask(task: String) {
        viewModelScope.launch {
            todoRepository.addTask(task)
        }
    }

    fun updateTask(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTask(todo)
        }
    }

    fun removeTask(todo: Todo) {
        viewModelScope.launch {
            todoRepository.removeTask(todo)
        }
    }
}