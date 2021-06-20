package com.example.planner.popup

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.planner.Category
import com.example.planner.R
import com.example.planner.EventProperty
import kotlinx.android.synthetic.main.add_event.view.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.M)
class Popup(
    var popupModel: PopupViewModel, popupContentView:View,
    viewLifecycleOwner:LifecycleOwner,
    context: Context?,
    activity: Activity,
    event: EventProperty?
) {

    val popup = PopupWindow(activity!!)
    init {

        popup.setFocusable(true);
        popup.setOutsideTouchable(true);
        popup.contentView = popupContentView

        //set month&year
        val showMonth: TextView = popupContentView.showMonth
        val showYear: TextView = popupContentView.showYear

        showMonth.text = popupModel.month.value
        showYear.text = popupModel.year.value.toString()
        popupContentView.apply {
            prevMonth.setBackgroundResource(R.drawable.left_arrow)
            prevYear.setBackgroundResource(R.drawable.left_arrow)
            nextMonth.setBackgroundResource(R.drawable.right_arrow)
            nextYear.setBackgroundResource(R.drawable.right_arrow)
        }
        popupModel.setBtnMonthYear(popupContentView)


        popupModel.month.observe(viewLifecycleOwner, Observer {
            showMonth.text = it
        })
        popupModel.year.observe(viewLifecycleOwner, Observer {
            showYear.text = it.toString()
        })

        //set seekbar
        val dayBar = popupContentView.day_bar
        val dayShow = popupContentView.day_show_text
        var progressChangedValue = 1
        val curDay = popupModel.day.value!!
        var offset = curDay

        dayShow.text = offset.toString()
        dayBar.setProgress(0)
        dayBar.setMax(31 - offset)
        dayBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress + offset;
                dayShow.text = progressChangedValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        popupModel.setDate.observe(viewLifecycleOwner, Observer {
            if (it==true) {
                offset = popupModel.day.value!!

                dayBar.setMax(popupModel.checkDayofMonth(showMonth.text.toString()))
                dayShow.text = offset.toString()
                dayBar.setProgress(offset)

                popupModel.finishedChangeM()
            }
        })
        popupModel.changeM.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                offset = 1
                if (showMonth.text.toString() == popupModel.months.get(popupModel.currentMonth)) {
                    offset = curDay
                }
                Log.i("adpa","changeMonth"+offset.toString())
                dayBar.setMax(popupModel.checkDayofMonth(showMonth.text.toString()) - offset)
                dayBar.setProgress(0)
                dayShow.text = offset.toString()

                popupModel.finishedChangeM()
            }
        })

        //set type spinner
        val typeSpinner: Spinner = popupContentView.add_type
        var typeList: Array<String> = arrayOf<String>("Info", "Warning", "Emergency")

        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!, android.R.layout.simple_expandable_list_item_1, typeList
        )
        typeSpinner.setAdapter(adapterType)

        //set cat spinner
        val catSpinner: Spinner = popupContentView.select_cat
        popupModel.onGetAllCat()
        popupModel.allCat.observe(viewLifecycleOwner, Observer {
            var catListtmp: Array<Category> = it.toTypedArray()
            var catList = Array(catListtmp.size){""}
            for (i in 0..(catListtmp.size-1)){
                catList[i] = catListtmp[i].cat
            }
            val adapterCat: ArrayAdapter<String> = ArrayAdapter<String>(
                context!!, android.R.layout.simple_expandable_list_item_1, catList
            )
            catSpinner.setAdapter(adapterCat)
        })

        //set add new cat button
        var addCat = false
        popupContentView.add_cat_btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                addCat = true
                popupContentView.add_cat.visibility = View.VISIBLE
                popupContentView.select_cat.visibility = View.GONE
            }

        })
        //set Submit button
        popupContentView.sunmit_add_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (event==null) {
                    popupModel.insert(popupContentView)
                }
                else{
                    popupModel.update(popupContentView,event.id)
                }
            }
        })
        popupModel.updating.observe(viewLifecycleOwner, Observer {
            if (it==true){
                popup.dismiss()
            }
        })
        setDefaultValue(event,popupContentView,popupModel,popupContentView)




    }

    fun setDefaultValue(event: EventProperty?, view:View, model:PopupViewModel,popupContentView:View){
        if (event == null){
            Log.i("adpa","null")
            val curMonth = Calendar.getInstance().time.month
            val curYear = Calendar.getInstance().time.year + 1900
            val curDay = Calendar.getInstance().time.date

            view.apply {
                add_title_text.setText(null)
                add_detail_text.setText(null)
            }
            model.setDate(curDay, curMonth, curYear)
            popupContentView.add_type.setSelection(0)
        }
        else {
            view.apply {
                add_title_text.setText(event.title)
                add_detail_text.setText(event.detail)
            }
            model.setDate(event.day, event.month, event.year)
            popupContentView.add_type.setSelection(event.type)
        }
    }

    //set Edit button
}
