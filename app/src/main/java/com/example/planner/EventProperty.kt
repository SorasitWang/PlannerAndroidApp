package com.example.planner

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TypeEvent { INFO, WARN, EMER }


@Entity(tableName = "event_table")
data class EventProperty(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "category")
    var cat: String = "",

    @ColumnInfo(name = "type")
    var type: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "TITLE",

    @ColumnInfo(name = "detail")
    var detail: String = "...",

    @ColumnInfo(name = "day")
    var day: Int = -1,

    @ColumnInfo(name = "month")
    var month: Int = 0,

    @ColumnInfo(name = "year")
    var year: Int = 0)

@Entity(tableName = "cat_table")
data class Category(
    @PrimaryKey(autoGenerate = false)
    var cat : String = ""
)