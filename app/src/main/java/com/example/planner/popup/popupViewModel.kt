package com.example.planner.popup

import android.R
import android.app.Application
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.*
import com.example.planner.EventProperty
import com.example.planner.EventViewModel
import com.example.planner.databinding.AddEventBinding
import kotlinx.android.synthetic.main.add_event.view.*
import java.util.*

class PopupViewModel(val viewModel: EventViewModel,app: Application) : AndroidViewModel(app) {

    val months = viewModel.months
    val types = viewModel.types

    private val _year = MutableLiveData<Int>()
    val year : LiveData<Int>
        get() = _year

    private var _month = MutableLiveData<String>()
    val month : LiveData<String>
        get() = _month
        /*Transformations.map(_month){
        _month.value?.let { it1 -> months.get(it1) }
    }*/

    /*private var _cat = MutableLiveData<Int>()
    val cat = Transformations.map(_cat){
        _cat.value?.let { it1 -> cats.get(it1) }
    }*/

    private var _type = MutableLiveData<Int>()
    val type = Transformations.map(_type){
        _type.value?.let { it1 -> types.get(it1) }
    }

    init {
        Log.i("popup","init")
        _month.value = "4"  //Calendar.MONTH
        _year.value = 4 //Calendar.YEAR
    }

    fun nextMonth(){
        //_month.value = _month.value!!.plus(1)%months.size
        Log.i("popup",month.value.toString())

    }
    fun prevMonth(){
        //_month.value = _month.value!!.minus(1).plus(months.size)%months.size
        Log.i("popup",month.value.toString())
    }
    fun nextYear(){
        _year.value = _year.value!!.plus(1)

    }
    fun prevYear(){
        _year.value = _year.value!!.minus(1)

    }


}