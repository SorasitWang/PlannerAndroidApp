package com.example.planner.popup

import android.R
import android.app.Application
import android.os.Build
import android.service.autofill.Validators.not
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.planner.*
import com.example.planner.databinding.AddEventBinding
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.android.synthetic.main.grid_view_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.BlockingDeque
import kotlin.math.min

class PopupViewModel(val database : EventDatabaseDAO, viewModel: EventViewModel,app: Application) : AndroidViewModel(app) {

    val months = viewModel.months
    val types = viewModel.types
    val currentMonth = Calendar.getInstance().time.month
    val currentYear = Calendar.getInstance().time.year + 1900
    val currentDay = Calendar.getInstance().time.date

    lateinit var catDatabase : CatDatabaseDAO

    private val _day = MutableLiveData<Int>()
    val day : LiveData<Int>
        get() = _day

    private val _year = MutableLiveData<Int>()
    val year : LiveData<Int>
        get() = _year

    private var _month = MutableLiveData<Int>()
    val month : LiveData<String>
        get() = Transformations.map(_month){
        _month.value?.let { it1 -> months.get(it1) }
    }

    private val _changeM = MutableLiveData<Boolean>()
    val changeM : LiveData<Boolean>
        get() = _changeM

    /*private var _cat = MutableLiveData<Int>()
    val cat = Transformations.map(_cat){
        _cat.value?.let { it1 -> cats.get(it1) }
    }*/

    private var _type = MutableLiveData<Int>()
    val type = Transformations.map(_type){
        _type.value?.let { it1 -> types.get(it1) }
    }

    private var _eventId = MutableLiveData<EventProperty>()
    val eventId : LiveData<EventProperty>
        get() = _eventId

    private var _setDate = MutableLiveData<Boolean>()
    val setDate : LiveData<Boolean>
        get() = _setDate

    private var _updating = MutableLiveData<Boolean>()
    val updating : LiveData<Boolean>
        get() = _updating

    private var _allCat = MutableLiveData<List<Category>>()
    val allCat : LiveData<List<Category>>
        get() = _allCat

    init {

        catDatabase = EventDatabase.getInstance(app).catDatabaseDao
        //onInsertCat("Default")
        _month.value = currentMonth  //Calendar.MONTH
        _year.value = currentYear //Calendar.YEAR
        _day.value = currentDay
    }

    fun setBtnMonthYear(popupContentView:View){
        popupContentView.prevMonth.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                prevMonth()
            }
        })
        popupContentView.nextMonth.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                nextMonth()
            }
        })
        popupContentView.prevYear.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                prevYear()
            }
        })
        popupContentView.nextYear.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                nextYear()
            }
        })
    }

    fun checkMonth(assign : Int){
        if (year.value!! > currentYear || assign >= currentMonth){
            _month.value = assign
        }
    }
    fun nextMonth(){
       checkMonth(_month.value!!.plus(1)%months.size)
        _changeM.value = true
    }
    fun prevMonth(){
        checkMonth(_month.value!!.minus(1).plus(months.size)%months.size)
        _changeM.value = true
    }
    fun nextYear(){
        _year.value = _year.value!!.plus(1)

    }
    fun prevYear(){
        _year.value = min(_year.value!!.minus(1),currentYear)

    }
    fun finishedChangeM(){
        _changeM.value = false
    }

    fun checkDayofMonth(m : String) : Int{
        if (m in listOf<String>("April","June","November","September")){
            return 30
        }
        return 31
    }
    fun insert(popup : View){
        var tmp = EventProperty()
        tmp.apply {
            title = popup.add_title_text.text.toString()
            detail = popup.add_detail_text.text.toString()
            type = popup.add_type.selectedItemPosition
            day = popup.day_show_text.text.toString().toInt()
            month = months.indexOf(popup.showMonth.text.toString())
            year = popup.showYear.text.toString().toInt()
        }
        viewModelScope.launch {
            database.insert(tmp)
            _updating.value = true
        }
    }
    fun update(popup : View,id:Int){
        Log.i("database","update")
        var tmp = EventProperty()
        tmp.apply {
            title =  popup.add_title_text.text.toString()
            detail = popup.add_detail_text.text.toString()
            type = popup.add_type.selectedItemPosition
            day = popup.day_show_text.text.toString().toInt()
            month = months.indexOf(popup.showMonth.text.toString())
            year = popup.showYear.text.toString().toInt()
        }
        tmp.id = id
        viewModelScope.launch {
            database.update(tmp)
            _updating.value = true
        }
    }
    fun finishedUpdate(){
        _updating.value = false
    }
    fun onGetById(id :Int) {
        viewModelScope.launch{
           getById(id)
        }
    }
    suspend fun getById(id:Int){
        var tmp : EventProperty
        withContext(Dispatchers.IO) {
             tmp = database.getById(id)
        }
        _eventId.value = tmp
    }
    fun setDate(d:Int,m:Int,y:Int){
        _month.value = m
        _day.value = d
        _year.value = y
        _setDate.value = true
    }
    fun finishedSetDate(){
        _setDate.value = false
    }
    fun onGetAllCat() {
        viewModelScope.launch {
            getAllCat()
        }
    }
    suspend fun getAllCat(){
        var tmp : List<Category>
        withContext(Dispatchers.IO){
            tmp = catDatabase.getAll()
        }
        _allCat.value = tmp
    }

    fun onInsertCat(cat:String){
        viewModelScope.launch {
            insertCat(cat)
        }
    }
    suspend fun insertCat(cat:String){
        withContext(Dispatchers.IO){
            catDatabase.insert(Category(cat))
        }

    }



}