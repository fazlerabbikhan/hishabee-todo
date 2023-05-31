package com.fazlerabbikhan.hishabeetodo.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.fazlerabbikhan.hishabeetodo.R
import com.fazlerabbikhan.hishabeetodo.model.Todo
import com.fazlerabbikhan.hishabeetodo.viewModel.TodoViewModel
import kotlinx.coroutines.*

class TodoAdapter(private val viewModel: TodoViewModel) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var todoList: List<Todo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todoList[position]
        holder.bind(currentTodo)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(todos: List<Todo>) {
        todoList = todos
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        private val completeButton: AppCompatImageView = itemView.findViewById(R.id.completeImageView)
        private val deleteButton: AppCompatImageView = itemView.findViewById(R.id.deleteImageView)

        @OptIn(DelicateCoroutinesApi::class)
        fun bind(todo: Todo) {
            taskTextView.text = todo.task

            if (todo.isComplete) {
                completeButton.setImageResource(R.drawable.ic_tic_mark)
            } else {
                completeButton.setImageResource(R.drawable.ic_tic_mark_outline)
            }

            completeButton.setOnClickListener {
                val updatedTask = todo.copy(isComplete = !todo.isComplete)
                viewModel.updateTask(updatedTask)
            }

            deleteButton.setOnClickListener {
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        viewModel.removeTask(todo)
                    }
                }
            }
        }
    }
}