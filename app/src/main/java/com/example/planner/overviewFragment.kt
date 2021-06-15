package com.example.planner

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.planner.databinding.FragmentOverviewBinding
import com.example.planner.R

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
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding.lifecycleOwner = this
        binding.recycleView.adapter = EventAdapter()
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        binding.viewModel = viewModel
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