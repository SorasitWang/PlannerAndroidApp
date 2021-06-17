package com.example.planner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.databinding.FragmentOverviewBinding
import com.example.planner.popup.Popup


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
    lateinit var binding : FragmentOverviewBinding
    private lateinit var viewModel : EventViewModel
    private lateinit var popUpView : Popup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentOverviewBinding.inflate(inflater)
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
        val manager = GridLayoutManager(activity, 1)
        binding.recycleView.layoutManager = manager

        binding.addBtn.setOnClickListener{

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
            Log.i("adapter", "clcikAdd")
            /*val ds = DisplayMetrics()*/
            val i = Intent(this.activity,Popup::class.java)
            startActivity(i)
            /*
            if (it == true) {
                val intent = Intent(activity, PopupWindow::class.java)
                intent.putExtra("popuptitle", "Error")
                intent.putExtra("popuptext", "Sorry, that email address is already used!")
                intent.putExtra("popupbtn", "OK")
                intent.putExtra("darkstatusbar", false)
                startActivity(intent)
            }*/
       })

        setUp()

        return binding.root
    }



    fun setUp(){
        binding.leftBtnMonth.setImageResource(R.drawable.left_arrow)
        binding.leftBtnType.setImageResource(R.drawable.left_arrow)

        binding.rightBtnMonth.setImageResource(R.drawable.right_arrow)
        binding.rightBtnType.setImageResource(R.drawable.right_arrow)

    }

}
