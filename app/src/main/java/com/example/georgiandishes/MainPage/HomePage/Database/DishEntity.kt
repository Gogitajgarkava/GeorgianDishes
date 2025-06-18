package com.example.georgiandishes.MainPage.HomePage.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val imageUrl: String = "",
    val region: String = ""
)

