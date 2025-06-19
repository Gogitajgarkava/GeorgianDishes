package com.example.georgiandishes.MainPage.HomePage

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.georgiandishes.R


class DetailDishFragment : Fragment(R.layout.fragment_detail_dish) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val titleTextView = view.findViewById<TextView>(R.id.textViewTitle)
        val descriptionTextView = view.findViewById<TextView>(R.id.textViewDescription)
        val recipeTextView = view.findViewById<TextView>(R.id.textViewRecept)

        val args = arguments
        val name = args?.getString("dish_name")
        val description = args?.getString("dish_description")
        val recipe = args?.getString("dish_recipe")



        name?.let { titleTextView.text = it }
        description?.let { descriptionTextView.text = it }
        recipe?.let {recipeTextView.text = it }

    }

}

