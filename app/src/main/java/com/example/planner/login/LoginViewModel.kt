package com.example.planner.login

import android.transition.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    private val _signUpMode = MutableLiveData<Boolean>()
    val signUpMode : LiveData<Boolean>
        get() = _signUpMode

    private val _headerLogin = MutableLiveData<String>()
    val headerLogin : LiveData<String>
        get() = _headerLogin

    private val _showSignupBtn = MutableLiveData<Int>()
    val showSignupBtn :  LiveData<Int>
        get() = _showSignupBtn


    init{
        Log.i("Firebase", "init")
        _showSignupBtn.value = View.VISIBLE
        _headerLogin.value =  "Login"






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

    fun openSignupMode(){
        _headerLogin.value = "Signup"
        _showSignupBtn.value = View.GONE
        _signUpMode.value = true
        //change label

    }
    fun closeSigupMode(){
        _signUpMode.value = false
        //change label
        _headerLogin.value = "Login"
        _showSignupBtn.value = View.VISIBLE
    }
    fun checkExist(name:String,pwd:String){
        viewModelScope.launch {
            docRef.document(name).get().addOnSuccessListener{ document ->
                if (document.data == null)
                    addUser(name,pwd)
                else{

                }
            } }
    }

    fun addUser(name:String,pwd:String){
        viewModelScope.launch{
            val data = hashMapOf(
                "password" to pwd,
                "events" to null
            )
            docRef.document(name)
                .set(data)
                .addOnSuccessListener {
                    Log.i("Firebase", "DocumentSnapshot successfully written!")
                    _signUpMode.value = false
                }
                .addOnFailureListener { e -> Log.w("Firebase", "Error writing document", e) }
            }
        }


    }
        /*v*/


