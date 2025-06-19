package com.example.georgiandishes.MainPage

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.example.georgiandishes.R

class FragmentAdd : Fragment(R.layout.fragment_add) {


    private lateinit var spinnerRegion: Spinner
    private lateinit var selectedRegion: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinnerRegion = view.findViewById(R.id.spinnerRegion)
        val btnAdd = view.findViewById<Button>(R.id.button6)
        val etName = view.findViewById<EditText>(R.id.editTextText)
        val etDescription = view.findViewById<EditText>(R.id.editTextText2)
        val etRecipe = view.findViewById<EditText>(R.id.editTextText4)
        val etImageUrl = view.findViewById<EditText>(R.id.editTextText6)


        val regions = listOf(
            "სამეგრელო", "იმერეთი", "კახეთი", "აჭარა", "აფხაზეთი", "გურია", "რაჭა - ლეჩხუმი", "სამცხე - ჯავახეთი", "ქვემო ქართლი","შიდა ქართლი","მცხეთა - მთიანეთი"
        )


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, regions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRegion.adapter = adapter


        spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                selectedRegion = regions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion = regions[0]
            }
        }


        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val recipe = etRecipe.text.toString().trim()
            val imageUrl = etImageUrl.text.toString().trim()

            if (name.isEmpty() || description.isEmpty() || recipe.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(requireContext(), "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            } else {

                uploadDataToFirebase(name, description, recipe, imageUrl, selectedRegion)
            }
        }
    }

    private fun uploadDataToFirebase(
        name: String,
        description: String,
        recipe: String,
        imageUrl: String,
        region: String
    ) {
        val databaseRef = FirebaseDatabase.getInstance("https://georgian-cuisine-guide-default-rtdb.europe-west1.firebasedatabase.app").reference.child("dishes").push()

        val dishMap = mapOf(
            "name" to name,
            "description" to description,
            "recipe" to recipe,
            "imageUrl" to imageUrl,
            "region" to region
        )

        databaseRef.setValue(dishMap)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "მონაცემები დამატებულია!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_fragmentAdd_to_fragmentHome)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "დამატება ვერ მოხერხდა", Toast.LENGTH_SHORT).show()
            }
    }
}
