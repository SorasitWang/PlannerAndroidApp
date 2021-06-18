package com.example.planner

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.databinding.AddEventBinding
import com.example.planner.databinding.FragmentOverviewBinding
import com.example.planner.popup.PopupViewModelFactory
import com.example.planner.popup.PopupViewModel
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import java.util.Calendar



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [overviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class overviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val binding: FragmentOverviewBinding by lazy {
        FragmentOverviewBinding.inflate(
            layoutInflater
        )
    }
    private val popBinding: AddEventBinding by lazy {
        AddEventBinding.inflate(
            layoutInflater
        )
    }

    lateinit var popUp : PopupWindow
    lateinit var layout : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rootLayout.foreground.alpha = 0
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = EventDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = EventViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(EventViewModel::class.java)

        binding.viewModel = viewModel
        var adapter = EventAdapter()
        binding.recycleView.adapter = adapter
        val manager = GridLayoutManager(this.activity, 1)
        binding.recycleView.layoutManager = manager

        binding.addBtn.setOnClickListener {

        }
        viewModel.updating.observe(viewLifecycleOwner, Observer {

            if (it == true) {

                viewModel.events?.let {
                    Log.i("adapter", "detect")
                    adapter.submitList(it)
                }
                viewModel.finishedUpdateFilter()
            }
        })
        viewModel.openAddView.observe(viewLifecycleOwner, Observer {

            //MainActivity.onClick()
            Log.i("adapter", "clickAdd")
            binding.rootLayout.foreground.alpha = 220
            popUp.showAtLocation(view, Gravity.CENTER, 0, 0)

        })
        setupPopup(viewModel)
        setupOverview()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setupOverview() {
        //image for button
        binding.leftBtnMonth.setImageResource(R.drawable.left_arrow)
        binding.leftBtnType.setImageResource(R.drawable.left_arrow)

        binding.rightBtnMonth.setImageResource(R.drawable.right_arrow)
        binding.rightBtnType.setImageResource(R.drawable.right_arrow)


    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupPopup(viewModel : EventViewModel){
        val application = requireNotNull(this.activity).application

        val popupviewModelFactory =  PopupViewModelFactory(viewModel,application)

        val popupModel =
            ViewModelProvider(this,  popupviewModelFactory).get(PopupViewModel::class.java)

        popBinding.lifecycleOwner = this
        popBinding.viewModel = popupModel




        //popup
        val popupContentView: View =
            LayoutInflater.from(this.activity).inflate(R.layout.add_event, null)
        popUp = PopupWindow(activity!!)
        layout = FrameLayout(activity!!)
        popUp.contentView = popupContentView


        popUp.setOutsideTouchable(true);
        popUp.setOnDismissListener {
            binding.rootLayout.foreground.alpha = 0
        }
        popUp.setFocusable(true);



        //set seekbar
        val dayBar = popupContentView.day_bar
        val dayShow = popupContentView.day_show_text

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayBar.setMin(1)
        }
        dayBar.setMax(31)
        dayBar.setProgress(1)
        dayBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progressChangedValue = 0

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress;
                dayShow.text = progressChangedValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //dayShow.text = progressChangedValue.toString()
            }
        })


        //set spinner
        val spinner : Spinner = popupContentView.add_type
        var typeList : Array<String> = arrayOf<String>("Info", "Warning", "Emergency")

        val adapterThai: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!,android.R.layout.simple_expandable_list_item_1,typeList)
        spinner.setAdapter(adapterThai)

        //set month&year
        val showMonth : TextView = popupContentView.showMonth
        /*val idxMonth = 0
        showMonth.text = viewModel.months.get(idxMonth)*/

    }
}
