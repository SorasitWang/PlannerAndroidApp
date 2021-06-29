package com.example.planner.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.planner.R
import com.example.planner.databinding.CatFragmentBinding
import com.example.planner.databinding.LoginFragmentBinding
import com.google.firebase.firestore.FirebaseFirestore


class loginFragment : Fragment() {

    companion object {
        fun newInstance() = loginFragment()
    }
    private lateinit var binding : LoginFragmentBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.submit.observe(viewLifecycleOwner, Observer {
            if (viewModel.signUpMode.value==false) {
                if (it == true){
                    viewModel.logIn(binding.inputUser.text.toString(),binding.inputPassword.text.toString())
                    viewModel.finishedSubmit()
                }
            }
            else{
                viewModel.checkExist(binding.inputUser.toString(),binding.inputPassword.toString())
            }

        })
        viewModel.valid.observe(viewLifecycleOwner, Observer {
            if (it == false)
                Toast.makeText(context,"Invalid username or password", Toast.LENGTH_SHORT).show()
            else{
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }



}