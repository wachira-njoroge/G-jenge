package com.awesome.g_jenge.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Projects(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name="code")
    val code: String,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: String,
)