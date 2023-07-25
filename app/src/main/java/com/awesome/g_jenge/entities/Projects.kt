package com.awesome.g_jenge.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Projects(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,

    @ColumnInfo(name="code")
    val code: String,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: String,
)