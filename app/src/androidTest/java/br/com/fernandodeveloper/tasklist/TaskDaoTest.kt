package br.com.fernandodeveloper.tasklist

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.fernandodeveloper.tasklist.feature.task.data.AppDatabase
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.local.TaskDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskDao: TaskDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        taskDao = db.taskDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllTasks() {
        //Given - Nothing

        //When
        val allTasks = taskDao.getAllTasks().waitForValue()

        //Then
        assertEquals(allTasks.size, 0)
    }

    @Test
    fun insertAndGetAllTasks() {
        //Given
        val task = TaskDto(name = "task")

        //When
        val id = taskDao.insert(task)

        //Then
        val allTasks = taskDao.getAllTasks().waitForValue()
        assertEquals(allTasks[0].name, task.name)
        assertEquals(allTasks[0].id, id)
    }


    @Test
    fun deleteAllTasks() {
        //Given
        val task = TaskDto(name = "task")
        taskDao.insert(task)

        //When
        taskDao.deleteAll()

        //Then
        val allTasks = taskDao.getAllTasks().waitForValue()
        assert(allTasks.isEmpty())

    }

    @Test
    fun getTaskById() {
        //Given
        val task = TaskDto(name = "task")
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()
        val clickedTask = allTasks[0]

        //When
        val taskById = taskDao.getById(clickedTask.id).waitForValue()

        //Then
        assertEquals(taskById.name, clickedTask.name)

    }

    @Test
    fun deleteTaskById() {
        //Given
        val task = TaskDto(name = "task")
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()
        val clickedTask = allTasks[0]

        //When
        taskDao.deleteById(clickedTask.id)

        //Then
        val refreshTasks = taskDao.getAllTasks().waitForValue()
        assertEquals(refreshTasks.size, allTasks.size - 1)

    }

    @Test
    fun updateTask() {
        //Given
        val task = TaskDto(name = "task")
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()
        val updatedTask = allTasks[0].copy(name = "NewName")

        //When
        taskDao.update(updatedTask)

        //Then
        val refreshTasks = taskDao.getAllTasks().waitForValue()
        assertEquals(refreshTasks[0].name, updatedTask.name)

    }

}