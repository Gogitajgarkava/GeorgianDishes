package com.example.georgiandishes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.georgiandishes.databinding.ActivityMainBinding //დარწმუნდით რომ ეს სწორია
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavView = binding.bottomNavView
        bottomNavView.setupWithNavController(navController)
        bottomNavView.setBackgroundColor(Color.TRANSPARENT)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentSingIn, R.id.fragmentCreateNewAccount, R.id.fragmentForgetPassword -> {

                    setBottomNavigationVisibility(View.GONE)
                }
                else -> {
                    setBottomNavigationVisibility(View.VISIBLE)
                }
            }
        }
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToMainScreen()
        } else {

            goToSignInScreen()
        }
    }
    private fun goToMainScreen() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.action_fragmentSingIn_to_fragmentHome)
    }
    private fun goToSignInScreen() {
        setBottomNavigationVisibility(View.GONE)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.fragmentSingIn)
    }
    fun setBottomNavigationVisibility(visibility: Int) {
        binding.bottomNavView.visibility = visibility
    }

}
