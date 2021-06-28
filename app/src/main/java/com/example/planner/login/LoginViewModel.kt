package com.example.planner.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    init{
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["middle"] = "Mathison"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID

        // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            "Firebase",
                            "DocumentSnapshot added with ID: " + documentReference.id
                        )
                    }
                    .addOnFailureListener { e -> Log.w("Firebase" "Error adding document", e) }

        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

}