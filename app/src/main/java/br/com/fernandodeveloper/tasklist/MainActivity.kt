package br.com.fernandodeveloper.tasklist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.fernandodeveloper.tasklist.feature.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.presentation.TaskListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        taskViewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GetApplication.instance)
        ).get(TaskListViewModel::class.java)

        taskViewModel.allTasks.observe(this, {
            Toast.makeText(this, it.size.toString(), Toast.LENGTH_SHORT).show()
        })

        taskViewModel.addTask(TaskDto(name = "Prova de Certificação"))
    }
}