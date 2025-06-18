package com.example.georgiandishes.MainPage.HomePage

import DishAdapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.georgiandishes.MainPage.HomePage.Database.*
import com.example.georgiandishes.R
import kotlinx.coroutines.launch

class FragmentHome : Fragment(R.layout.fragment_home) {

    private lateinit var regionRecyclerView: RecyclerView
    private lateinit var dishesRecyclerView: RecyclerView
    private lateinit var regionAdapter: RegionAdapter
    private lateinit var dishAdapter: DishAdapter
    private lateinit var dishViewModel: DishViewModel

    private val regions = listOf(
        Region("სამეგრელო", "https://zugdidi.gov.ge/img/contact/3_1703402037.jpg"),
        Region("იმერეთი", "https://mbg.ge/uploads/locblogs/large/85766c71b1b1d869fb61457faa46a547.jpg"),
        Region("კახეთი", "https://storage.georgia.travel/images/alaverdi.webp"),
        Region("აჭარა", "https://www.turebi.ge/uploads/photos/tours1/large/33045_2.jpg?v=0"),
        Region("აფხაზეთი", "https://storage.googleapis.com/feedc_v1_largepostimages/MTY5MTIwX3Bvc3RfMTU5NTMyNTM5Mw==.jpeg"),
       // Region("გურია", "https://atravel.home.blog/wp-content/uploads/2019/01/411x261-1-1200x675.png"),
        Region("რაჭა - ლეჩხუმი", "https://mbg.ge/uploads/locblogs/large/59b4e2b97df203eec6d87259f15d139b.jpg"),
        Region("სამცხე - ჯავახეთი", "https://storage.georgia.travel/images/vardzia-in-samtskhe-javakheti.webp"),
        Region("ქვემო ქართლი", "https://cdn.georgiantravelguide.com/storage/files/kveshis-tsikhe-qveshis-tsikhe-kvemo-kartli.jpg"),
        Region("შიდა ქართლი", "https://cdn.georgiantravelguide.com/storage/files/shida-kartli-shida-kartli.jpg"),
        Region("მცხეთა - მთიანეთი", "https://cdn.georgiantravelguide.com/storage/files/sveticxoveli-church-svetitskhoveli.jpg")
    )


    private var allDishes: List<DishEntity> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regionRecyclerView = view.findViewById(R.id.regionRecyclerView)
        dishesRecyclerView = view.findViewById(R.id.dishesRecyclerView)


        val db = AppDatabase.getDatabase(requireContext())
        val repository = DishRepository(db.dishDao())
        val factory = DishViewModelFactory(repository)
        dishViewModel = ViewModelProvider(this, factory)[DishViewModel::class.java]


        regionAdapter = RegionAdapter(regions) { selectedRegion ->
            updateDishesForRegion(selectedRegion.name)
        }
        regionRecyclerView.adapter = regionAdapter

        dishAdapter = DishAdapter(emptyList())
        dishesRecyclerView.adapter = dishAdapter

        fetchDishesOnce()


        dishViewModel.dishes.observe(viewLifecycleOwner) { data ->
            allDishes = data
            val initialRegion = regions.first().name
            updateDishesForRegion(initialRegion)
        }
    }


    private fun fetchDishesOnce() {
        viewLifecycleOwner.lifecycleScope.launch {
            dishViewModel.fetchFromFirebase()
        }
    }

    private fun updateDishesForRegion(region: String) {
        val filtered = allDishes.filter { it.region == region }
        dishAdapter.updateData(filtered.map { it.toDish() })
    }
}
