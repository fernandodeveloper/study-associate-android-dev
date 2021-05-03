package br.com.fernandodeveloper.tasklist.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.fernandodeveloper.tasklist.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER by name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskDto: TaskDto): Long

    @Query("SELECT * FROM task WHERE id=:id")
    fun getById(id: Long): LiveData<TaskDto>

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("DELETE FROM task WHERE id=:id")
    fun deleteById(id: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskDto: TaskDto)


}