package com.example.georgiandishes.MainPage


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FragmentMainScreen : Fragment(R.layout.fragment_main_screen) {
    private lateinit var userNameTextView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameTextView = view.findViewById(R.id.textViewUserName)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://georgian-cuisine-guide-default-rtdb.europe-west1.firebasedatabase.app")


        val userId = auth.currentUser?.uid

        if (userId != null) {

            val usersRef = database.getReference("users")
            usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val name = snapshot.child("name").value as String? // ან String::class.java
                    val lName = snapshot.child("lName").value as String?


                    userNameTextView.text = "მოგესალმებით, $name $lName!"
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "მონაცემების წაკითხვის შეცდომა: ${error.message}")
                    userNameTextView.text = "მონაცემების წაკითხვის შეცდომა"
                }
            })
        } else {
            userNameTextView.text = "მომხმარებელი არ არის ავტორიზებული"
        }
    }
}