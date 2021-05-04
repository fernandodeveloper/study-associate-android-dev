package br.com.fernandodeveloper.tasklist.feature.task.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.fernandodeveloper.tasklist.GetApplication
import br.com.fernandodeveloper.tasklist.R
import br.com.fernandodeveloper.tasklist.databinding.ActivityTaskDetailBinding
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.Status
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import com.google.android.material.snackbar.Snackbar


class TaskDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var selectedTask: TaskDto
    private lateinit var selectedStatus: String
    private lateinit var binding: ActivityTaskDetailBinding

    private val status = arrayOf(
        Status.TODO.name,
        Status.PROGRESS.name,
        Status.DONE.name
    )

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startStatus()
        viewModel = ViewModelProvider(
            this,
            TaskDetailViewModel.TaskViewModelFactory(
                GetApplication.instance
            )
        ).get(TaskDetailViewModel::class.java)

        viewModel.task.observe(this, {
            selectedTask = it
            binding.edtTaskDetailName.setText(it.name)

            status.forEachIndexed { index, item ->
                if (item == it.state) {
                    selectedStatus = item
                    binding.spinnerTaskDetailStatus.setSelection(index)
                }
            }
        })

        val id = intent.getLongExtra(EXTRA_TASK_ID, 0)
        viewModel.start(id)

        binding.btnUpdate.setOnClickListener {
            val name = binding.edtTaskDetailName.text.toString()
            if (name.isNotEmpty()) {
                val updatedTask = selectedTask.copy(name = name, state = selectedStatus)
                viewModel.update(taskDto = updatedTask)
                Snackbar.make(binding.btnUpdate, R.string.updated_successful, Snackbar.LENGTH_LONG)
                    .show()
                finish()
            } else {
                Toast.makeText(this, R.string.name_required, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_detail, menu)
        return super.onCreateOptionsMenu(menu)

    }

    private fun startStatus() {
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            status
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTaskDetailStatus.adapter = adapter

        binding.spinnerTaskDetailStatus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = status[position]
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_delete -> {
                viewModel.task.removeObservers(this)
                viewModel.delete(selectedTask.id)
                Snackbar.make(binding.btnUpdate, R.string.deleted_successful, Snackbar.LENGTH_LONG)
                    .show()
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object {

        private const val EXTRA_TASK_ID = "EXTRA_TASK_DETAIL_ID"

        /**
         * Start [TaskDetailActivity]
         * @param context previous activity
         */
        fun start(context: Context, taskId: Long): Intent {
            return Intent(context, TaskDetailActivity::class.java)
                .apply { putExtra(EXTRA_TASK_ID, taskId) }
        }
    }
}