package com.example.georgiandishes.MainPage.HomePage.Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DishViewModelFactory(private val repository: DishRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DishViewModel(repository) as T
    }
}