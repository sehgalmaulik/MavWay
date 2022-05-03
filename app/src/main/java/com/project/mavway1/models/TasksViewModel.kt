package com.project.mavway1.models

//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
//import com.project.mavway1.database.TasksDatabase
//import com.project.mavway1.repository.TasksRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class TasksViewModel(application: Application) : AndroidViewModel(application) {
//
//    val repository: TasksRepository
//    var allTasks: LiveData<List<Tasks>>
//
//    init{
//        val dao = TasksDatabase.getDatabase(application).getTasksDao()
//        repository = TasksRepository(dao)
//        allTasks = repository.allTasks
//
//    }
//
//    fun deleteTask(task: Tasks) = viewModelScope.launch(Dispatchers.IO) {
//        repository.delete(task)
//    }
//
//    fun insertTask(task: Tasks) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(task)
//    }
//
//}