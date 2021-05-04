package br.com.fernandodeveloper.tasklist.feature.task.list.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fernandodeveloper.tasklist.GetApplication
import br.com.fernandodeveloper.tasklist.databinding.ActivityListBinding
import br.com.fernandodeveloper.tasklist.feature.task.list.presentation.TaskListViewModel
import br.com.fernandodeveloper.tasklist.feature.task.add.TaskAddActivity

class TaskListActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter =
            TaskListAdapter(
                this,
                ::taskListClickListener
            )
        binding.rvTaskList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GetApplication.instance)
        ).get(TaskListViewModel::class.java)

        setObserver()

        binding.fabAddTask.setOnClickListener {
            val intent =
                TaskAddActivity.start(
                    this
                )
            startActivity(intent)
        }

    }

    private fun taskListClickListener(taskId: Long) {
//        startActivity(TaskDetailActivity.start(this, taskId))
    }

    private fun setObserver() {
        viewModel.alltasks.observe(this, Observer {
            adapter.submit(it)
        })
    }
}