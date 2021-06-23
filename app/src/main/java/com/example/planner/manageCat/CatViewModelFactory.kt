package com.example.planner.manageCat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planner.CatDatabaseDAO
import com.example.planner.EventDatabaseDAO
import com.example.planner.EventViewModel

class CatViewModelFactory(
    private val dataSource: EventDatabaseDAO,
    private val catDatabase: CatDatabaseDAO
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            return CatViewModel(dataSource,catDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
