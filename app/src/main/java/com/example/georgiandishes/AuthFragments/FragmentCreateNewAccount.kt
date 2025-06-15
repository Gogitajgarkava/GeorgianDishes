package com.example.georgiandishes.AuthFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentCreateNewAccount : Fragment(R.layout.fragment_create_new_account) {

    private lateinit var userName: EditText
    private lateinit var userLName: EditText
    private lateinit var userBirth: EditText
    private lateinit var userEmail: EditText
    private lateinit var Password: EditText
    private lateinit var cPassword: EditText
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = view.findViewById(R.id.Eiditname)
        userLName = view.findViewById(R.id.EiditLname)
        userBirth = view.findViewById(R.id.editTextDate)
        userEmail = view.findViewById(R.id.editTextTextEmailAddress)
        Password = view.findViewById(R.id.editTextTextPassword2)
        cPassword = view.findViewById(R.id.editTextTextPassword2)
        button = view.findViewById(R.id.button)

        button.setOnClickListener {
            registerUser()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_createAccount_to_signIn)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun registerUser() {
        val name = userName.text.toString()
        val lName = userLName.text.toString()
        val birth = userBirth.text.toString()
        val email = userEmail.text.toString()
        val password = Password.text.toString()
        val confirmPassword = cPassword.text.toString()

        if (name.isEmpty() || lName.isEmpty() || birth.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(context, "ელ-ფოსტა არასწორია", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPassword(password)) {
            Toast.makeText(context, "პაროლი არ აკმაყოფილებს მოთხოვნებს", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(context, "პაროლები არ ემთხვევა", Toast.LENGTH_SHORT).show()
            return
        }

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "მომხმარებელი წარმატებით დარეგისტრირდა", Toast.LENGTH_SHORT).show()
                    val database = FirebaseDatabase.getInstance("https://georgian-cuisine-guide-default-rtdb.europe-west1.firebasedatabase.app")
                    val usersRef = database.getReference("users")
                    val user = mapOf(
                        "name" to name,
                        "lName" to lName,
                        "birth" to birth,
                        "email" to email
                    )
                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser?.uid

                    userId?.let {
                        usersRef.child(it).setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(context, "მონაცემები წარმატებით შეინახა", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_createAccount_to_signIn)
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firebase", "მონაცემების შენახვის შეცდომა: ${e.message}")
                                Toast.makeText(context, "მონაცემების შენახვის შეცდომა: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } ?: run {

                        Toast.makeText(context, "მომხმარებელი ვერ დარეგისტრირდა", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "რეგისტრაციის შეცდომა: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
