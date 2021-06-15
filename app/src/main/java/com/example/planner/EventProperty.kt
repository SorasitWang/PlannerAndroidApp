package com.example.planner

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class TypeEvent(val value: String) { INFO("info"), WARN("warning"), EMER("emergency") }

@Parcelize
data class EventProperty(
    val id: String,
    val title: TypeEvent,
    val detail: String,
    val date: String) : Parcelable {
}