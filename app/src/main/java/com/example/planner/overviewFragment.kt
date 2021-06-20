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

        val viewModelFactory = EventViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(EventViewModel::class.java)

        binding.viewModel = viewModel
        var adapter = EventAdapter(EventAdapter.OnClickListener {
            viewModel.onDelete(it)
            viewModel.finishedUpdate()
        })
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
            Log.i("adapter", "clickAdd")
            binding.rootLayout.foreground.alpha = 220
            popupView.popup.showAtLocation(view, Gravity.CENTER, 0, 0)

        })
        setupPopup(viewModel,dataSource)
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
    fun setupPopup(viewModel : EventViewModel,database : EventDatabaseDAO) {
        val application = requireNotNull(this.activity).application

        val popupviewModelFactory =  PopupViewModelFactory(database,viewModel,application)

        val popupModel =
            ViewModelProvider(this,  popupviewModelFactory).get(PopupViewModel::class.java)
        val popupContentView: View =
            LayoutInflater.from(this.activity).inflate(R.layout.add_event, null)

        popupView = Popup(popupModel,popupContentView,viewLifecycleOwner,context,activity!!)
        popupView.popup.setOnDismissListener {
            binding.rootLayout.foreground.alpha = 0
        }
    }

}
