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


    var events = database.getByMonth(0,0)

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
        _year.value = 0
        _month.value = 0
        _type.value = 0
        var tmp = EventProperty()
        tmp.year = 0
        tmp.month = 0
        viewModelScope.launch {
            database.insert(tmp)
            getEvent(_month.value!!, _type.value!!)

        }

    }
    private suspend fun getEvent(m : Int , y : Int){
        withContext(Dispatchers.IO) {
            events = database.getByMonth(m,y)
            if (events == null)  {
                Log.i("adapter","null")
            }
        }
        //use those value to filtering
    }
    public suspend fun insert(night: EventProperty) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
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