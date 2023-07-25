package com.awesome.g_jenge.services

import androidx.annotation.WorkerThread
import com.awesome.g_jenge.dao.ProjectsDao
import com.awesome.g_jenge.entities.Projects
import kotlinx.coroutines.flow.Flow

class ProjectsService(private val projectsDao: ProjectsDao) {
    // Room executes all queries on a separate thread, the Observed Flow will notify the observer when the data changes.
    val allProjects = projectsDao.getAllProjects()
    @WorkerThread
    suspend fun insertProject(project: Projects) {
        return projectsDao.insert(project)
    }
    //get project by name
    fun projectByName(name:String): Flow<Projects> {
        return projectsDao.getProjectByName(name)
    }
    @WorkerThread
    suspend fun deleteProject(code: String): Void {
        return projectsDao.deleteProject(code)
    }
}