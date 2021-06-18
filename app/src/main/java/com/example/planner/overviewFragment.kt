package com.example.planner

import android.os.Build
import com.example.planner.R
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
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
    private val binding: FragmentOverviewBinding by lazy {
        FragmentOverviewBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var viewModel: EventViewModel
    private lateinit var popUpView: Popup
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
            val popupContentView: View =   LayoutInflater.from(this.activity).inflate(R.layout.add_event, null)
            popUp = PopupWindow(activity!!)
            layout = FrameLayout(activity!!)
            popUp.contentView = popupContentView
            binding.rootLayout.foreground.alpha = 220
            popUp.setOutsideTouchable(true);
            popUp.setOnDismissListener {
                binding.rootLayout.foreground.alpha = 0
            }
            popUp.showAtLocation(view, Gravity.CENTER, 0, 0)

            /*
            val ds = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(ds)
            setUp()
            val width = ds.widthPixels
            val height = ds.heightPixels
            activity!!.getWindow().setLayout((width * 0.8).toInt(), (height * 0.5).toInt())
            *//*val ds = DisplayMetrics()
            val i = Intent(this,Popup::class.java)
            startActivity(i)

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
