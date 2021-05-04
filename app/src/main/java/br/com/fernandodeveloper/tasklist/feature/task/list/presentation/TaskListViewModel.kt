package br.com.fernandodeveloper.tasklist.feature.task.list.presentation

import android.app.Application
import androidx.lifecycle.*
import br.com.fernandodeveloper.tasklist.feature.task.data.AppDatabase
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val alltasks: LiveData<List<TaskDto>>

    init {
        val dao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        alltasks = repository.getAllTasks()

    }

    fun insert(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }

    fun update(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }


    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                TaskListViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}