package com.example.georgiandishes.MainPage.HomePage.Database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DishViewModel(private val repository: DishRepository) : ViewModel() {
    val dishes: LiveData<List<DishEntity>> = repository.getCachedDishes().asLiveData()

    fun fetchFromFirebase() {
        viewModelScope.launch {
            repository.fetchAndCacheDishes()
        }
    }
}
