package com.example.planner.manageCat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.CatDatabaseDAO
import com.example.planner.Category
import com.example.planner.EventDatabaseDAO
import kotlinx.coroutines.launch

class CatViewModel(var eventDatabase:EventDatabaseDAO , var catDatabase: CatDatabaseDAO) : ViewModel() {


    private val _allCat = MutableLiveData<List<Category>>()
    val allCat : LiveData<List<Category>>
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
            _allCat.value = catDatabase.getAll()
        }
    }
    fun countCatEvent(cat:String){

    }
    fun addCatView(){

    }
    fun insertCat(cat :Category){
        viewModelScope.launch {
            catDatabase.insert(cat)
            getALlCat()
        }
    }
    fun onDelete(cat : String){
        viewModelScope.launch {
            catDatabase.delete(cat)
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