package com.fazlerabbikhan.hishabeetodo
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fazlerabbikhan.hishabeetodo.adapter.TodoAdapter
import com.fazlerabbikhan.hishabeetodo.database.TodoDatabase
import com.fazlerabbikhan.hishabeetodo.databinding.ActivityMainBinding
import com.fazlerabbikhan.hishabeetodo.repository.TodoRepository
import com.fazlerabbikhan.hishabeetodo.viewModel.TodoViewModel
import com.fazlerabbikhan.hishabeetodo.viewModel.TodoViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        val repository = TodoRepository(todoDao)
        todoViewModel = ViewModelProvider(this,
            TodoViewModelFactory(repository))[TodoViewModel::class.java]

        // Set up RecyclerView
        todoAdapter = TodoAdapter(todoViewModel)
        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }

        // Observe the list of tasks from ViewModel
        todoViewModel.getAllTodos().observe(this) { todos ->
            todoAdapter.setTasks(todos)
        }

        binding.addButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_add_task, null)

            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add Task")
                .setPositiveButton("Add") { _, _ ->
                    val taskEditText = dialogView.findViewById<EditText>(R.id.taskEditText)
                    val task = taskEditText.text.toString().trim()
                    if (task.isNotEmpty()) {
                        todoViewModel.addTask(task)
                    }
                }
                .setNegativeButton("Cancel", null)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}