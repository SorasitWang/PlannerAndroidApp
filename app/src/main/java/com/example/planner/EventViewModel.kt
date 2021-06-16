package com.example.planner

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventViewModel( val database: EventDatabaseDAO,app: Application): AndroidViewModel(app)  {
    private val _months = listOf<String>("January","February","March","April ","May","June","July","August","September","October","November","December")
    private val _types = listOf<String>("Info","Warning","Emergency")
    //lateinit var events :  LiveData<List<EventProperty?>>

    private val _year = MutableLiveData<Int>()
    val year : LiveData<Int>
        get() = _year

    private var _month = MutableLiveData<Int>()
    val month = Transformations.map(_month){
        _month.value?.let { it1 -> _months.get(it1) }
    }

    private var _type = MutableLiveData<Int>()
    val type = Transformations.map(_type){
            _type.value?.let { it1 -> _types.get(it1) }
        }

    init{
        Log.i("EventViewModel","init")
        _year.value = 2021
        _month.value = 5
        _type.value = 0
        viewModelScope.launch {
            getEvent()
        }

    }
    private suspend fun getEvent() {
        withContext(Dispatchers.IO) {
            //events = database.getByMonth(_month.value!!, _year.value!!)
        }
        //use those value to filtering
    }

    fun nextMonth(){
        _month.value = _month.value!!.plus(1)%12
    }
    fun prevMonth(){
        _month.value = _month.value!!.minus(1).plus(12)%12
    }
    fun nextType(){
        _type.value = _type.value!!.plus(1)%3
    }
    fun prevType(){
        _type.value = _type.value!!.minus(1).plus(3)%3
    }
}