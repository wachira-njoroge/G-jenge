package com.awesome.g_jenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awesome.g_jenge.entities.Projects
import kotlinx.coroutines.flow.Flow
@Dao
interface ProjectsDao {
    @Query("select * from projects order by name asc")
    fun getAllProjects(): Flow<List<Projects>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project:Projects)

    @Query("select * from projects where `projects`.name =:projectName")
    fun getProjectByName(projectName:String): Flow<Projects>
    //
    @Query("DELETE FROM projects where `projects`.code = :code")
    suspend fun deleteProject(code: String)
}