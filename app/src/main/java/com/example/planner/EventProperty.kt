package com.example.planner

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TypeEvent { INFO, WARN, EMER }

@Entity(tableName = "event_table")
data class EventProperty(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category")
    val cat: String = "All",

    @ColumnInfo(name = "type")
    val type: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "TITLE",

    @ColumnInfo(name = "detail")
    val detail: String = "...",

    @ColumnInfo(name = "day")
    val day: Int = -1,

    @ColumnInfo(name = "month")
    var month: Int = 0,

    @ColumnInfo(name = "year")
    var year: Int = 0)