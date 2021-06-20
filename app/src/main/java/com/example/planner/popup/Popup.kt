package com.example.planner.popup

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.add_event.view.*

@RequiresApi(Build.VERSION_CODES.M)
class Popup(
    var popupModel: PopupViewModel, popupContentView:View,
    viewLifecycleOwner:LifecycleOwner,
    context: Context?,
    activity : Activity
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

        popupModel.changeM.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                offset = 1
                if (showMonth.text.toString() == popupModel.months.get(popupModel.currentMonth)) {
                    offset = curDay
                }
                dayBar.setMax(popupModel.checkDayofMonth(showMonth.text.toString()) - offset)
                dayShow.text = offset.toString()
                dayBar.setProgress(0)

                popupModel.finishedChangeM()
            }
        })

        //set spinner
        val spinner: Spinner = popupContentView.add_type
        var typeList: Array<String> = arrayOf<String>("Info", "Warning", "Emergency")

        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!, android.R.layout.simple_expandable_list_item_1, typeList
        )
        spinner.setAdapter(adapterType)


        //set Submit button
        popupContentView.sunmit_add_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupModel.insert(popupContentView)
                popup.dismiss()
            }
        })
    }

    //set Edit button
}
