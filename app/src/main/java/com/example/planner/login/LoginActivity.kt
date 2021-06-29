package com.example.planner.login

import android.os.Bundle
import android.text.Layout
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.planner.R
import com.example.planner.databinding.ActivityMainBinding
import com.example.planner.databinding.LoginFragmentBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var drawerLayout : ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")

        val binding =
            DataBindingUtil.setContentView<LoginFragmentBinding>(this, R.layout.login_fragment)
        drawerLayout = binding.loginLayout

        Log.i("Firebase", "login init")
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i("Firebase", "Inside onComplete function!");
                for (document in task.result!!) {
                    val name = document.data["event"].toString()
                    Log.i("Firebase", name)
                }
            }
        }
        Log.i("Firebase", "After attaching the listener!");
    }
        /*
        docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->
            Log.d("Firebase", "aaa")
            if (e != null) {
                Log.w("Firebase", "Listen failed.", e)
                return@EventListener
            }
            if (documentSnapshot != null && documentSnapshot.exists()) {
                Log.d("Firebase", "sss")
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    val userInfo = documentSnapshot.toString()
                    Log.d("Firebase", userInfo)
                }
            } else {

                Log.d("Firebase", "Current data: null")
            }

        })
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Firebase", "ssss")
                }
                Log.d("Firebase", "empty")
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents.", exception)
            }

    }*/


}