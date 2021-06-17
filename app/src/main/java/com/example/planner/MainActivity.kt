package com.example.planner

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.planner.R
import com.example.planner.databinding.FragmentOverviewBinding

class MainActivity : AppCompatActivity() {

    lateinit var popup: PopupWindow
    private lateinit var binding : FragmentOverviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}