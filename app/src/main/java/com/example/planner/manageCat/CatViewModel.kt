package com.example.planner.manageCat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planner.Category

class CatViewModel : ViewModel() {
    private val _updating = MutableLiveData<Boolean>(false)
    val updating : LiveData<Boolean>
        get() = _updating

    private val _editView = MutableLiveData<Boolean>(false)
    val editView : LiveData<Boolean>
        get() = _editView

    init{

    }

    fun addCat(){

    }
    fun onDelete(cat : Category){

    }
    fun finishedUpdate(){
        _updating.value = false
    }
    fun editView(cat : Category){
        _editView.value = true
    }
    fun finishedEditView(){
        _editView.value = false
    }
    // TODO: Implement the ViewModel
}