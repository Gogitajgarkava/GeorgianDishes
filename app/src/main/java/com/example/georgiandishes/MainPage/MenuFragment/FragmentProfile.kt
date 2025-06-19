package com.example.georgiandishes.MainPage.MenuFragment


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentProfile : Fragment(R.layout.fragment_profile) {

    private lateinit var userName: EditText
    private lateinit var userLName: EditText
    private lateinit var userBirth: EditText
    private lateinit var userEmail: TextView
    private lateinit var buttonSave: Button
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = view.findViewById(R.id.editTextText)
        userLName = view.findViewById(R.id.editTextText2)
        userBirth = view.findViewById(R.id.editTextText4)
        userEmail = view.findViewById(R.id.editTextText6)
        buttonSave = view.findViewById(R.id.button6)

        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        database = FirebaseDatabase.getInstance("https://georgian-cuisine-guide-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")

        userId?.let {
            database.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val lName = snapshot.child("lName").getValue(String::class.java)
                        val birth = snapshot.child("birth").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)

                        userName.setText(name)
                        userLName.setText(lName)
                        userBirth.setText(birth)
                        userEmail.text = email
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Failed to load user data.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        buttonSave.setOnClickListener {
            updateUserData(userId)
        }
    }

    private fun updateUserData(userId: String?) {
        val name = userName.text.toString()
        val lName = userLName.text.toString()
        val birth = userBirth.text.toString()

        if (userId != null) {
            val userUpdates = mapOf(
                "name" to name,
                "lName" to lName,
                "birth" to birth
            )

            database.child(userId).updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to update user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
