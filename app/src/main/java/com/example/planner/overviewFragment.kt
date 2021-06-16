package com.example.planner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.EventViewModel
import com.example.planner.databinding.FragmentOverviewBinding
import com.example.planner.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    private lateinit var binding : FragmentOverviewBinding
    private lateinit var viewModel : EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding  = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = EventDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = EventViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(EventViewModel::class.java)

        binding.viewModel = viewModel
        val adapter = EventAdapter()
        binding.recycleView.adapter = adapter
        val manager = GridLayoutManager(activity,1)
        binding.recycleView.layoutManager = manager

        binding.addBtn.setOnClickListener{

        }
        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let{
                Log.i("overViewFragment","add")
                adapter.submitList(it)
            }
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