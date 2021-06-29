package com.example.planner.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var user = ""
    var password = 0
    val docRef = FirebaseFirestore.getInstance().collection("users")

    private val _submit = MutableLiveData<Boolean>()
    val submit : LiveData<Boolean>
        get() = _submit

    private val _valid = MutableLiveData<Boolean>()
    val valid : LiveData<Boolean>
        get() = _valid
    init{
        Log.i("Firebase", "init")







    }

    fun submit(){
        _submit.value = true
        Log.i("login","submit")
    }
    fun finishedSubmit(){
        _submit.value = false
    }

    fun logIn(user:String,pwd:String) {
        Log.i("login",user + pwd)
        viewModelScope.launch {
            docRef.document(user).get().addOnSuccessListener{ document ->
                Log.i("login",document.data?.get("password").toString())
                if (document.data?.get("password").toString() != pwd){
                    _valid.value = false
            }

        } }
    }
        /*v*/
    }

