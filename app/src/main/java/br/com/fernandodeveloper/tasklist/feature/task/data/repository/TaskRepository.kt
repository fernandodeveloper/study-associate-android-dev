package br.com.fernandodeveloper.tasklist.feature.task.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.local.TaskDao

class TaskRepository private constructor(private val localDataSource: TaskDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addTask(taskDto: TaskDto) {
        localDataSource.insert(taskDto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(taskDto: TaskDto) {
        localDataSource.update(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> = localDataSource.getAllTasks()

    fun getTaskById(taskId: Long): LiveData<TaskDto> = localDataSource.getById(taskId)

    fun deleteById(taskId: Long){
        localDataSource.deleteById(taskId)
    }


    companion object {
        fun create(localDataSource: TaskDao): TaskRepository {
            return TaskRepository(localDataSource)
        }
    }
}