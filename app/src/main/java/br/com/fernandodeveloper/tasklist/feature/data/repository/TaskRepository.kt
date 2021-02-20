package br.com.fernandodeveloper.tasklist.feature.data.repository

import androidx.lifecycle.LiveData
import br.com.fernandodeveloper.tasklist.feature.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.data.local.TaskDao

class TaskRepository private constructor(private val localDataSource: TaskDao) {

    suspend fun addTask(taskDto: TaskDto) {
        localDataSource.insertTask(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> = localDataSource.getAllTasks()


    companion object {
        fun create(localDataSource: TaskDao): TaskRepository {
            return TaskRepository(localDataSource)
        }
    }
}