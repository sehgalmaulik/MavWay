package com.project.mavway1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.mavway1.models.Tasks

@Dao
interface TasksDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): LiveData<List<Tasks>>
}