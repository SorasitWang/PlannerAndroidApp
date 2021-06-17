package com.example.planner

import android.app.Application
import android.app.usage.UsageEvents
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventViewModel( val database: EventDatabaseDAO,app: Application): AndroidViewModel(app)  {
    private val _months = listOf<String>("January","February","March","April ","May","June","July","August","September","October","November","December")
    private val _cats = listOf<String>("Default")
    private val _types = listOf<String>("Info","Warning","Emergency")

    private val _year = MutableLiveData<Int>()
    val year : LiveData<Int>
        get() = _year

    private var _month = MutableLiveData<Int>()
    val month = Transformations.map(_month){
        _month.value?.let { it1 -> _months.get(it1) }
    }
    private var _cat = MutableLiveData<Int>()
    val cat = Transformations.map(_cat){
        _cat.value?.let { it1 -> _cats.get(it1) }
    }

    private var _type = MutableLiveData<Int>()
    val type = Transformations.map(_type){
            _type.value?.let { it1 -> _types.get(it1) }
        }

    private var _updating = MutableLiveData<Boolean>()
    val updating : LiveData<Boolean>
        get() = _updating

    private var _openAddView = MutableLiveData<Boolean>()
    val openAddView : LiveData<Boolean>
        get() = _openAddView
    var  events = listOf<EventProperty>() //database.getByMonth(_month.value!!, _year.value!!)
    init{
        Log.i("EventViewModel","init")
        _month.value = 0
        _year.value = 0
        _type.value = 0
        _cat.value = 0
        var tmp = EventProperty()
        tmp.year = 0
        tmp.month = 1
        tmp.type = 2
        tmp.title = "Hello"

        var tmp2 = EventProperty()
        tmp2.apply{
            year = 0
            month = 0
            type = 0
            title = "Title"
        }

        var tmp3 = EventProperty()
        tmp2.apply{
            year = 0
            month = 0
            type = 1
            title = "World"
        }


        viewModelScope.launch {
            //database.clearAll()
            events = database.getByMonth(0, 0)
            updateFilter()
        }


    }

    fun updateFilter(){
       viewModelScope.launch {
            getEvent(_month.value!!, _year.value!!,_cat.value!!)
           _updating.value = true
        }
    }

    fun finishedUpdateFilter(){
        _updating.value = false
    }

    private suspend fun getEvent(m : Int , y : Int,c : Int){
        withContext(Dispatchers.IO) {
            Log.i("adapter","updateFilter")
            if (_type.value == 0){
                events = database.getByMonth(m, y)
            }
            else {
                events = database.getByCat(m,y,c)
            }
            if (events == null)  {
                Log.i("adapter",_month.value.toString() + " , " + _year.value.toString())
            }
        }
        //use those value to filtering
    }
    suspend fun insert(night: EventProperty) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }
    fun nextMonth(){
        _month.value = _month.value!!.plus(1)%_months.size
        updateFilter()
    }
    fun prevMonth(){
        _month.value = _month.value!!.minus(1).plus(_months.size)%_months.size
        updateFilter()
    }
    fun nextCat(){
        _cat.value = _cat.value!!.plus(1)%_cats.size
        updateFilter()
    }
    fun prevCat(){
        _cat.value = _cat.value!!.minus(1).plus(_cats.size)%_cats.size
        updateFilter()
    }
    fun openAddView(){
        _openAddView.value = true
    }

}