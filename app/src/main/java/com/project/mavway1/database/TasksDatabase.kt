package com.project.mavway1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mavway1.dao.TasksDAO
import com.project.mavway1.models.Tasks

@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TasksDatabase: RoomDatabase() {

    abstract fun getTasksDao(): TasksDAO

    companion object {
        @Volatile
        private var INSTANCE: TasksDatabase? = null

        fun getDatabase(context: Context): TasksDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }
}