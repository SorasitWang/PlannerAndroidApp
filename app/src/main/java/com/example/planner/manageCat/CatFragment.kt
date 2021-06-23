package com.example.planner.manageCat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.EventAdapter
import com.example.planner.R
import com.example.planner.databinding.CatFragmentBinding
import com.example.planner.databinding.FragmentOverviewBinding

class CatFragment : Fragment() {

    companion object {
        fun newInstance() = CatFragment()
    }
    private val binding: CatFragmentBinding by lazy {
        CatFragmentBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var viewModel: CatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.viewModel = viewModel
        var adapter = CatAdapter(
            CatAdapter.OnClickListener {
            viewModel.onDelete(it)
            viewModel.finishedUpdate()
        },
            CatAdapter.OnClickListener {
                viewModel.editView(it)
            }
        )
        binding.recycleCat.adapter = adapter
        binding.recycleCat.layoutManager = GridLayoutManager(this.activity, 1)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}