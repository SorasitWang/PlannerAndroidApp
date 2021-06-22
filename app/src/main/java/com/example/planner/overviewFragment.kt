package com.example.planner

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.databinding.FragmentOverviewBinding
import com.example.planner.popup.Popup
import com.example.planner.popup.PopupViewModelFactory
import com.example.planner.popup.PopupViewModel
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.android.synthetic.main.grid_view_item.view.*
import java.io.ObjectStreamException


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
    lateinit var popupModel :PopupViewModel
    lateinit var popupContentView : View
    lateinit var popupView : Popup

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
        val catDatabase = EventDatabase.getInstance(application).catDatabaseDao
        val viewModelFactory = EventViewModelFactory(dataSource, catDatabase,application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(EventViewModel::class.java)

        binding.viewModel = viewModel
        var adapter = EventAdapter(EventAdapter.OnClickListener {
            viewModel.onDelete(it)
            viewModel.finishedUpdate() },
            EventAdapter.OnClickListener {
                viewModel.openEditView(it)}
        )
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = GridLayoutManager(this.activity, 1)

        viewModel.updating.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.events?.let {
                    Log.i("adapter", "detect")
                    adapter.submitList(it)
                }
                viewModel.finishedUpdate()
            }
        })

        viewModel.openAddView.observe(viewLifecycleOwner, Observer {
            //MainActivity.onClick()
            if (it == true) {
                Log.i("adapter", "clickAdd")
                setupPopup(viewModel, dataSource,-1)
                binding.rootLayout.foreground.alpha = 220
                popupView.popup.showAtLocation(view, Gravity.CENTER, 0, 0)
                viewModel.finishedOpenAddView()
            }
        })
        viewModel.openEditView.observe(viewLifecycleOwner, Observer {
            //MainActivity.onClick()
            if (it != null) {
                setupPopup(viewModel, dataSource,it )
                binding.rootLayout.foreground.alpha = 220
                popupView.popup.showAtLocation(view, Gravity.CENTER, 0, 0)
                viewModel.finishedOpenEditView()
            }
        })

        val popupviewModelFactory =  PopupViewModelFactory(dataSource,catDatabase,viewModel,application)

        popupModel =
            ViewModelProvider(this,  popupviewModelFactory).get(PopupViewModel::class.java)
        popupContentView =
            LayoutInflater.from(this.activity).inflate(R.layout.add_event, null)
        popupView = Popup(popupModel,popupContentView,viewLifecycleOwner,context,activity!!,
            EventProperty()
        )

        popupModel.updating.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.events?.let {
                    Log.i("adapter", "detectUpdate")
                    viewModel.updateFilter()
                }
                popupModel.finishedUpdate()
            }
        })
        popupModel.updateCat.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.updateCat()
                viewModel. events?.let {
                    Log.i("overview", "updateCat")
                    viewModel.updateFilter()
                }
                popupModel.finishedUpdateCat()
            }
        })

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
    fun setupPopup(viewModel : EventViewModel, database : EventDatabaseDAO,id:Int) {
        if (id != -1){
            popupModel.onGetById(id)

        }
        else{
            popupView = Popup(popupModel,popupContentView,viewLifecycleOwner,context,activity!!,null)
            popupView.popup.setOnDismissListener {
                Log.i("ui","dismiss")
                binding.rootLayout.foreground.alpha = 0
            }
            }
        popupModel.eventId.observe(viewLifecycleOwner, Observer {
            if (it.id == id) {
                popupView = Popup(
                    popupModel, popupContentView, viewLifecycleOwner, context, activity!!,
                    popupModel.eventId.value!!
                )
                popupView.popup.setOnDismissListener {
                    Log.i("ui","dismiss")
                    binding.rootLayout.foreground.alpha = 0
                }
            }
        })



    }

}
