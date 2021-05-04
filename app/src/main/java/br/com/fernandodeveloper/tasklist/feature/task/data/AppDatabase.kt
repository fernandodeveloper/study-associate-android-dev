package br.com.fernandodeveloper.tasklist.feature.task.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fernandodeveloper.tasklist.feature.task.data.entity.TaskDto
import br.com.fernandodeveloper.tasklist.feature.task.data.local.TaskDao

@Database(entities = [TaskDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDataBase(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "taskDB")
                .fallbackToDestructiveMigration()
                .build()

    }

}