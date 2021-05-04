package br.com.fernandodeveloper.tasklist.feature.task.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.fernandodeveloper.tasklist.GetApplication
import br.com.fernandodeveloper.tasklist.R
import br.com.fernandodeveloper.tasklist.databinding.ActivityNewTaskBinding
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import com.google.android.material.snackbar.Snackbar

class TaskAddActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskAddViewModel
    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            TaskAddViewModel.TaskViewModelFactory(
                GetApplication.instance
            )
        ).get(TaskAddViewModel::class.java)

        binding.btnTaskNewSave.setOnClickListener {
            val name = binding.edtTaskNewName.text.toString()
            if (name.isNotEmpty()) {
                viewModel.insert(TaskDto(name = name))
                finish()
            } else {
                Snackbar.make(binding.edtTaskNewName, R.string.name_required, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object {
        /**
         * Start [TaskAddActivity]
         * @param context previous activity
         */
        fun start(context: Context): Intent {
            return Intent(context, TaskAddActivity::class.java)
        }
    }
}