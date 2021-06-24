package com.example.planner.manageCat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.CatDatabaseDAO
import com.example.planner.Category
import com.example.planner.EventDatabaseDAO
import com.example.planner.StringInt
import kotlinx.coroutines.launch

class CatViewModel(val eventDatabase:EventDatabaseDAO , val catDatabase: CatDatabaseDAO) : ViewModel() {


    private val _allCat = MutableLiveData<List<StringInt>>()
    val allCat : LiveData<List<StringInt>>
        get() = _allCat

    private val _updating = MutableLiveData<Boolean>(false)
    val updating : LiveData<Boolean>
        get() = _updating

    private val _editView = MutableLiveData<Boolean>(false)
    val editView : LiveData<Boolean>
        get() = _editView

    init{
        getALlCat()
    }

    fun getALlCat(){
        viewModelScope.launch {
            _allCat.value = eventDatabase.countByCat()
        }
    }
    fun countCatEvent(cat:String){
        return
    }
    fun addCatView(){

    }
    fun insertCat(cat :Category){
        viewModelScope.launch {
            catDatabase.insert(cat)
            getALlCat()
        }
    }
    fun tmp(){
        Log.i("database","tmp")
    }
    fun onDelete(cat : String){
        Log.i("database",cat)
        viewModelScope.launch {
            catDatabase.delete(cat)
            eventDatabase.clearCat(cat)
            getALlCat()
        }
    }
    fun finishedUpdate(){

    }
    fun editView(cat : String){
        _editView.value = true
    }
    fun finishedEditView(){
        _editView.value = false
    }
    // TODO: Implement the ViewModel
}