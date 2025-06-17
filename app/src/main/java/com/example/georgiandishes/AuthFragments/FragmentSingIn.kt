package com.example.georgiandishes.AuthFragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class FragmentSingIn : Fragment(R.layout.fragment_sing_in) {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var ForgotPasswordTextView: TextView
    private lateinit var CreateNewAccount: Button

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    fun isValidEmail(email: String): Boolean {
        return email.matches(emailPattern.toRegex())
    }

    private fun loginUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "წარმატებული ავტორიზაცია", Toast.LENGTH_SHORT).show()
                    emailEditText.text.clear()
                    passwordEditText.text.clear()
                    findNavController().navigate(R.id.fragmentHome)

                } else {
                    Toast.makeText(requireContext(), "შეცდომა: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEditText = view.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = view.findViewById(R.id.editTextTextPassword)
        loginButton = view.findViewById(R.id.button)
        ForgotPasswordTextView = view.findViewById(R.id.textView)
        CreateNewAccount = view.findViewById(R.id.button2)



        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email)) {
                    loginUser(email, password)
                } else {
                    Toast.makeText(requireContext(), "ელფოსტის ფორმატი არასწორია", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_SHORT).show()
            }
        }

        ForgotPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_forgetPassword)
        }

        CreateNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_createAccount)
        }


    }
}
