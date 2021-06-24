package com.example.planner.manageCat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.*
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
        val application = requireNotNull(this.activity).application
        val dataSource = EventDatabase.getInstance(application).sleepDatabaseDao
        val catDatabase = EventDatabase.getInstance(application).catDatabaseDao
        val viewModelFactory = CatViewModelFactory(dataSource, catDatabase)
        viewModel =   ViewModelProvider(this, viewModelFactory).get(CatViewModel::class.java)
        binding.viewModel = viewModel
        var adapter = CatAdapter(
            CatAdapter.OnClickListener {
            viewModel.onDelete(it.category)
            viewModel.finishedUpdate()
        },
            CatAdapter.OnClickListener {
                viewModel.editView(it.category)
            }
        )
        binding.recycleCat.adapter = adapter
        binding.recycleCat.layoutManager = GridLayoutManager(this.activity, 1)

        viewModel.allCat.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}