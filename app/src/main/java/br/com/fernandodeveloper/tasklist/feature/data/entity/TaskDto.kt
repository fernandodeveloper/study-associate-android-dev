package br.com.fernandodeveloper.tasklist.feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val state: String = Status.TODO.name
)

enum class Status {
    TODO,
    PROGRESS,
    DONE
}