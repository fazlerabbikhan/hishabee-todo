package com.fazlerabbikhan.hishabeetodo.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val task: String,
    val isComplete: Boolean = false
)
