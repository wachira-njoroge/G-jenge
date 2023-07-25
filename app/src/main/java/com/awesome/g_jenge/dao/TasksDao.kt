package com.awesome.g_jenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awesome.g_jenge.entities.Tasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query(value = "SELECT * FROM tasks WHERE `tasks`.projectId = :projectName")
    fun getAllProjectTasks(projectName:String): Flow<List<Tasks>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: Tasks)

    @Query(value =
        "UPDATE tasks SET " +
                "description = :description, " +
                "due_date = :date, " +
                "due_time = :time, " +
                "status = :status " +
                "WHERE code = :code"
    )
    suspend fun update(description:String, date: String, time: String, status:String, code:String)

    @Query(value = "DELETE FROM tasks WHERE code = :code")
    suspend fun delete(code: String)

    @Query(value = "UPDATE tasks SET status = :done WHERE code = :code")
    suspend fun updateTaskStatus(code: String, done: String ="Done")

    @Query(value = "UPDATE tasks SET notified = 'true' WHERE code = :code")
    suspend fun updateToNotified(code:String)
}