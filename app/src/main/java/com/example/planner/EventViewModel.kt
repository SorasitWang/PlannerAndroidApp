package com.example.planner

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class EventViewModel : ViewModel() {

    private val _month = MutableLiveData<String>()
    val month : LiveData<String>
        get() = _month

    private val _type = MutableLiveData<String>()
    val type : LiveData<String>
        get() = _type

    init{
        Log.i("EventViewModel","init")
        _month.value = "May"
        _type.value = "Default"
        getEvent()
    }
    private fun getEvent(){
        //use those value to filtering
    }

    fun nextMonth(){

    }
    fun prevMonth(){

    }
    fun nextType(){

    }
    fun prevType(){
        
    }
}