package com.example.georgiandishes.MainPage.MenuFragment


import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.georgiandishes.R


class FragmentAboutApp : Fragment(R.layout.fragment_about_app) {
    private lateinit var team: TextView
    private lateinit var version: TextView
    private lateinit var developers: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        team = view.findViewById(R.id.textViewTeam)
        version = view.findViewById(R.id.textViewVersion)
        developers = view.findViewById(R.id.textViewDevelopers)

        developers.setTypeface(null, Typeface.BOLD)
        version.setTypeface(null, Typeface.BOLD)
        team.setTypeface(null, Typeface.BOLD)



    }
}