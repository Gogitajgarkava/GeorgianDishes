package com.example.georgiandishes.MainPage.HomePage.Database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dishes: List<DishEntity>)

    @Query("SELECT * FROM dishes")
    fun getAllDishes(): Flow<List<DishEntity>>
}