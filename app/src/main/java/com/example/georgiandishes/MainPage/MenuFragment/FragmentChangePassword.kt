package com.example.georgiandishes.MainPage.MenuFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.example.georgiandishes.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class FragmentChangePassword : Fragment(R.layout.fragment_change_password) {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.button5.setOnClickListener {
            val currentPassword = binding.editTextTextPassword4.text.toString().trim()
            val newPassword = binding.editTextTextPassword5.text.toString().trim()
            val confirmNewPassword = binding.editTextTextPassword6.text.toString().trim()

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                Toast.makeText(context, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(newPassword)) {
                Toast.makeText(context, "ახალი პაროლი არ აკმაყოფილებს მოთხოვნებს", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword != confirmNewPassword) {
                Toast.makeText(context, "ახალი პაროლები არ ემთხვევა", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            changePassword(currentPassword, newPassword)
        }
    }


    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$"
        return password.matches(passwordPattern.toRegex())
    }
    private fun changePassword(currentPasswordStr: String, newPassword: String) {
        val user = auth.currentUser

        if (user == null) {
            Toast.makeText(context, "მომხმარებელი არ არის ავტორიზებული", Toast.LENGTH_SHORT).show()
            return
        }

        val email = user.email
        if (email == null) {
            Toast.makeText(context, "ელფოსტა არ არის მითითებული", Toast.LENGTH_SHORT).show()
            return
        }


        val credential = EmailAuthProvider.getCredential(email, currentPasswordStr)
        user.reauthenticate(credential)
            .addOnCompleteListener { reAuthTask ->
                if (reAuthTask.isSuccessful) {

                    user.updatePassword(newPassword)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(context, "პაროლი წარმატებით შეიცვალა", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(context, "პაროლის შეცდომა: ${updateTask.exception?.message}", Toast.LENGTH_LONG).show()
                                Log.e("Password Change", "პაროლის შეცდომა: ${updateTask.exception?.message}")
                            }
                        }
                } else {
                    Toast.makeText(context, "მიმდინარე პაროლი არასწორია", Toast.LENGTH_LONG).show()
                    Log.e("Re-authentication", "მიმდინარე პაროლი არასწორია: ${reAuthTask.exception?.message}")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
