package com.example.georgiandishes.MainPage.MenuFragment


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.georgiandishes.R


class FragmentContact : Fragment(R.layout.fragment_contact) {
    private lateinit var contact: TextView
    private lateinit var Phone: TextView
    private lateinit var Mail: TextView
    private lateinit var Website: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contact = view.findViewById(R.id.textView7)
        Phone = view.findViewById(R.id.textView4)
        Mail = view.findViewById(R.id.textView5)
        Website = view.findViewById(R.id.textView6)

    }

}