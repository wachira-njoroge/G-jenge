package com.awesome.g_jenge.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.awesome.g_jenge.dao.ProjectsDao
import com.awesome.g_jenge.dao.TasksDao
import com.awesome.g_jenge.entities.Projects
import com.awesome.g_jenge.entities.Tasks

@Database(entities = [Projects::class, Tasks::class], version=9, exportSchema = false)
abstract class GjengeDatabase: RoomDatabase() {
    companion object{
        //Singleton prevents opening multiple instances of the same database
        @Volatile
        private var INSTANCE: GjengeDatabase? = null
        fun dbInit(context: Context):GjengeDatabase{
            // if the INSTANCE is not null, return it otherwise create the database
            return INSTANCE ?: synchronized(this){
                val dbInstance = Room
                    .databaseBuilder(context.applicationContext,GjengeDatabase::class.java, "Gjenge")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = dbInstance
                return dbInstance
            }
        }
    }
    abstract fun projectsDao(): ProjectsDao
    abstract fun tasksDao(): TasksDao
}