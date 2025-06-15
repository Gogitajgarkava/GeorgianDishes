package com.example.georgiandishes

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        val mainView = findViewById<View>(R.id.main)
        mainView.post {
            if (auth.currentUser != null) {

                goToMainScreen()
            } else {

                goToSignInScreen()
            }
        }
    }
    private fun goToMainScreen() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.action_signIn_to_main)
    }
    private fun goToSignInScreen() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.action_main_to_signIn)
    }
}