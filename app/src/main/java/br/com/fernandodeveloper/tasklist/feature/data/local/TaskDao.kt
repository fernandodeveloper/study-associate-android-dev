package br.com.fernandodeveloper.tasklist.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fernandodeveloper.tasklist.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER by name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDto: TaskDto)


}