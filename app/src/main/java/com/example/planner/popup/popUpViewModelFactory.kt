package com.example.planner.popup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planner.CatDatabaseDAO
import com.example.planner.EventDatabaseDAO
import com.example.planner.EventViewModel

class PopupViewModelFactory(
    private val database: EventDatabaseDAO,
    private val catDatabase: CatDatabaseDAO,
    private val dataSource: EventViewModel,
    private val application: Application ): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopupViewModel::class.java)) {
            return PopupViewModel(database,catDatabase,dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

