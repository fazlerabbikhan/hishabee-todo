package com.fazlerabbikhan.hishabeetodo.database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.fazlerabbikhan.hishabeetodo.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): LiveData<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}