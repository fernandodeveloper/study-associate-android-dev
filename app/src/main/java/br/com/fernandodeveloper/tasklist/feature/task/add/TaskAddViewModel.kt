package br.com.fernandodeveloper.tasklist.feature.task.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fernandodeveloper.tasklist.feature.task.data.AppDatabase
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskAddViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    init {
        val dao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun insert(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }

    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskAddViewModel::class.java)) {
                TaskAddViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}