package com.awesome.g_jenge.services

import androidx.annotation.WorkerThread
import com.awesome.g_jenge.dao.TasksDao
import com.awesome.g_jenge.entities.Tasks
import kotlinx.coroutines.flow.Flow

class TasksService(private val tasksDao: TasksDao) {
    // Room executes all queries on a separate thread, the Observed Flow will notify the observer when the data changes.
    fun getProjectTasks(projectName: String): Flow<List<Tasks>> {
        return tasksDao.getAllProjectTasks(projectName)
    }
    @WorkerThread
    suspend fun insertTask(task: Tasks) {
        return tasksDao.insert(task)
    }
    @WorkerThread
    suspend fun updateTask(description:String, date: String, time: String, status: String, code:String){
        return tasksDao.update(description, date,time, status,code)
    }
    @WorkerThread
    suspend fun deleteTask(code:String){
        return tasksDao.delete(code)
    }
    @WorkerThread
    suspend fun updateTaskStatus(code: String) {
        return tasksDao.updateTaskStatus(code)
    }
    @WorkerThread
    suspend fun updateToNotified(code:String){
        return tasksDao.updateToNotified(code)
    }
}