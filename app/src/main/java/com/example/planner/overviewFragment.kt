package com.example.planner

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
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
    lateinit var popupModel: PopupViewModel
    lateinit var popupContentView: View
    lateinit var popupView: Popup
    lateinit var viewModel : EventViewModel
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
        val viewModelFactory = EventViewModelFactory(dataSource, catDatabase, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(EventViewModel::class.java)

        binding.viewModel = viewModel
        var adapter = EventAdapter(EventAdapter.OnClickListener {
            viewModel.onDelete(it)
            viewModel.finishedUpdate()
        },
            EventAdapter.OnClickListener {
                viewModel.openEditView(it)
            }
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
                Log.i("ui", "clickAdd")
                setupPopup(viewModel, dataSource, null)
                binding.rootLayout.foreground.alpha = 220
                if (!activity?.isFinishing()!!) {
                    popupView.popup.showAtLocation(view, Gravity.CENTER, 0, 0)
                    viewModel.finishedOpenAddView()
                }

            }
        })

        viewModel.openEditView.observe(viewLifecycleOwner, Observer {
            //MainActivity.onClick()
            if (it != null) {
                Log.i("ui", "clickEdit")
                setupPopup(viewModel, dataSource, it)
                binding.rootLayout.foreground.alpha = 220
                if (!activity?.isFinishing()!!) {
                    popupView.popup.showAtLocation(view, Gravity.CENTER, 0, 0)
                    viewModel.finishedOpenAddView()
                }


            }
        })

        val popupviewModelFactory =
            PopupViewModelFactory(dataSource, catDatabase, viewModel, application)

        popupModel =
            ViewModelProvider(this, popupviewModelFactory).get(PopupViewModel::class.java)
        popupContentView =
            LayoutInflater.from(this.activity).inflate(R.layout.add_event, null)
        val animFadein = AnimationUtils.loadAnimation(context, R.anim.floating);
        popupModel.updating.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                viewModel.events?.let {
                    Log.i("ui", "detectUpdate")
                    viewModel.updateFilter()
                }
                popupModel.finishedUpdate()
            }
        })
        popupModel.updateCat.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.updateCat()
                viewModel.events?.let {
                    Log.i("overview", "updateCat")
                    viewModel.updateFilter()
                }
                popupModel.finishedUpdateCat()
            }
        })
        viewModel.month.observe(viewLifecycleOwner, Observer {
            binding.monthText.startAnimation(animFadein)
        })
        setupOverview()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i("Main","onStart")
        viewModel.updateCat()
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
    fun setupPopup(viewModel: EventViewModel, database: EventDatabaseDAO, e: EventProperty?) {
            val popup = PopupWindow(requireActivity())
            popupView = Popup(
                popupModel, popupContentView, viewLifecycleOwner, context,popup,e)

            popupView.popup.setOnDismissListener {
                Log.i("ui","dismiss")
                binding.rootLayout.foreground.alpha = 0
            }

    }


}
