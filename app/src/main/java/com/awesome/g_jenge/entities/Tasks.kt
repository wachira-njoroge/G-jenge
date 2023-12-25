package com.awesome.g_jenge.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tasks",
    foreignKeys =
    [ForeignKey(entity = Projects::class, parentColumns = ["id"], childColumns = ["projectId"])],
    indices = [Index(value = ["code","projectId"])]
)
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name="code", index = true)
    val code: String,

    @ColumnInfo(name="description")
    val description: String,

    @ColumnInfo(name="status")
    val status: String,

    @ColumnInfo(name = "due_date")
    val due_date:String,

    @ColumnInfo(name = "due_time")
    val due_time: String,

    @ColumnInfo(name = "notified")
    val notified: Boolean,

    @ColumnInfo(name = "projectId", index = true)
    val projectId: Int
)