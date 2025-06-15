package com.example.georgiandishes.AuthFragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth

class FragmentForgetPassword : Fragment(R.layout.fragment_forget_password) {
    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.editTextTextEmailAddress2)
        resetButton = view.findViewById(R.id.button3)

        resetButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "ელფოსტით გამოგზავნილია პაროლის აღდგენის ლინკი", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_forgetPassword_to_signIn)
                        } else {
                            Toast.makeText(requireContext(), "შეცდომა: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "გთხოვთ შეიყვანოთ თქვენი ელფოსტა", Toast.LENGTH_SHORT).show()
            }
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_forgetPassword_to_signIn)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
