package br.com.fernandodeveloper.tasklist.feature.task.details

import android.app.Application
import androidx.lifecycle.*
import br.com.fernandodeveloper.tasklist.feature.task.data.AppDatabase
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository

    private val _id = MutableLiveData<Long>()
    private var _task: LiveData<TaskDto> = _id
        .switchMap { id ->
            repository.getTaskById(id)
        }

    val task: LiveData<TaskDto> = _task

    init {
        val dao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun start(id: Long) {
        _id.value = id
    }

    fun update(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(taskDto)
        }
    }

    fun delete(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteById(taskId)
        }
    }




    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
                TaskDetailViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}