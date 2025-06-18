package com.example.georgiandishes.MainPage.MenuFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.georgiandishes.R
import com.google.firebase.auth.FirebaseAuth

class FragmentMenu : Fragment(R.layout.fragment_menu) {

    // Declare views
    private lateinit var logOutButton: Button
    private lateinit var textViewProfile: TextView
    private lateinit var textViewChangePassword: TextView
    private lateinit var textViewRules: TextView
    private lateinit var textViewContact: TextView
    private lateinit var textViewAboutApp: TextView
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        logOutButton = view.findViewById(R.id.button4)
        textViewProfile = view.findViewById(R.id.textViewPRofile)
        textViewChangePassword = view.findViewById(R.id.textViewChangePassword)
        textViewRules = view.findViewById(R.id.textViewRules)
        textViewContact = view.findViewById(R.id.textViewContact)
        textViewAboutApp = view.findViewById(R.id.textViewAboutApp)


        logOutButton.setOnClickListener {
            signOut()
        }


        textViewProfile.setOnClickListener {
            onProfileClick()
        }

        textViewChangePassword.setOnClickListener {
            onChangePasswordClick()
        }

        textViewRules.setOnClickListener {
            onRulesClick()
        }

        textViewContact.setOnClickListener {
            onContactClick()
        }

        textViewAboutApp.setOnClickListener {
            onAboutAppClick()
        }
    }


    private fun signOut() {
        auth.signOut()
        Toast.makeText(requireContext(), "თქვენ გამოხვედით ანგარიშიდან", Toast.LENGTH_SHORT).show()

        findNavController().navigate(
            R.id.action_fragmentProfile_to_fragmentSingIn,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.fragmentProfile, true)
                .build()
        )
    }


    private fun onProfileClick() {

        findNavController().navigate(R.id.action_fragmentProfile_to_fragmentProfile2)

    }


    private fun onChangePasswordClick() {

        findNavController().navigate(R.id.action_fragmentProfile_to_fragmentChangePassword)


    }


    private fun onRulesClick() {
        val url = "https://touniver-my.sharepoint.com/:w:/g/personal/g_jgarkava_alte_edu_ge/EeppJYLsiKVFv_428oOgAqkBSGnjqRIbpjCoOn8sSpUB1w?e=MrPp7b"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }



    private fun onContactClick() {
        findNavController().navigate(R.id.action_fragmentProfile_to_fragmentContact)

    }


    private fun onAboutAppClick() {
        findNavController().navigate(R.id.action_fragmentProfile_to_fragmentAboutApp)


    }
}
