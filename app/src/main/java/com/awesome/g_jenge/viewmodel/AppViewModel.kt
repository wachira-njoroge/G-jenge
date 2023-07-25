package com.awesome.g_jenge.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.awesome.g_jenge.entities.Projects
import com.awesome.g_jenge.entities.Tasks
import com.awesome.g_jenge.room.GjengeDatabase
import com.awesome.g_jenge.services.ProjectsService
import com.awesome.g_jenge.services.TasksService
import kotlinx.coroutines.launch

//This class is intended to get data records to display on the UI, observe and update data changes on the UI
class AppViewModel(ctx: Context):ViewModel(){
    val database by lazy{
        GjengeDatabase.dbInit(ctx)
    }
    private val projectsService by lazy {
        ProjectsService(database.projectsDao())
    }
    private val tasksService by lazy {
        TasksService(database.tasksDao())
    }
    //PROJECTS DATA:-
    //
    //Get all projects
    val allProjects = projectsService.allProjects.asLiveData()
    //New Project insert
    suspend fun insertProject(project: Projects) = viewModelScope.launch{
        projectsService.insertProject(project)
    }
    //Get project by name
    fun projectByName(name:String): LiveData<Projects> {
        return projectsService.projectByName(name).asLiveData()
    }
    //delete a specific project
    fun deleteProject(code: String) = viewModelScope.launch {
        projectsService.deleteProject(code)
    }
    //
    //TASKS DATA:-
    fun getProjectTasks(project:String): LiveData<List<Tasks>> {
        return tasksService.getProjectTasks(project).asLiveData()
    }
    fun insertTask(task: Tasks) = viewModelScope.launch{
        tasksService.insertTask(task)
    }
    //update task details
    fun updateTask(description:String, date:String, time:String, status: String, code:String) =
        viewModelScope.launch {
            tasksService.updateTask(description, date,time, status, code)
        }

    //update task status
    fun updateTaskStatus(code: String) = viewModelScope.launch {
        tasksService.updateTaskStatus(code)
    }
    //update to notified after notification is sent
    fun updateToNotified(code:String) = viewModelScope.launch{
        tasksService.updateToNotified(code)
    }
    //delete task
    fun deleteTask(code:String) = viewModelScope.launch{
        tasksService.deleteTask(code)
    }
}