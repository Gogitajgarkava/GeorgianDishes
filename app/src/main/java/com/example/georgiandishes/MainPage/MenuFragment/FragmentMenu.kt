package com.example.georgiandishes.MainPage.MenuFragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth

class FragmentMenu : Fragment(R.layout.fragment_menu) {

    private lateinit var logOutButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        logOutButton = view.findViewById(R.id.button4)

        logOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(requireContext(), "თქვენ გამოხვედით ანგარიშიდან", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_fragmentProfile_to_fragmentSingIn)
    }
}
