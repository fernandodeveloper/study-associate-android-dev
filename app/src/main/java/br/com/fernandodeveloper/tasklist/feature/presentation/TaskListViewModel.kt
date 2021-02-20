package br.com.fernandodeveloper.tasklist.feature.presentation

import android.app.Application
import androidx.lifecycle.*
import br.com.fernandodeveloper.tasklist.feature.data.AppDatabase
import br.com.fernandodeveloper.tasklist.feature.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<TaskDto>>

    init {
        val dao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        allTasks = repository.getAllTasks()
    }

    fun addTask(taskDto: TaskDto) {
        viewModelScope.launch {
            repository.addTask(taskDto)
        }
    }


    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                TaskListViewModel(this.application) as T
            } else {
                throw  IllegalArgumentException("ViewModel Not Fount")
            }
        }
    }
}