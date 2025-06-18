package com.example.georgiandishes.MainPage.HomePage.Database

import android.util.Log
import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DishRepository(private val dishDao: DishDao) {

    private val database = FirebaseDatabase.getInstance("https://georgian-cuisine-guide-default-rtdb.europe-west1.firebasedatabase.app/")
    fun getCachedDishes() = dishDao.getAllDishes()

    suspend fun fetchAndCacheDishes() {
        val dishesRef = database.getReference("dishes")

        val dishesList = suspendCancellableCoroutine<List<DishEntity>> { cont ->
            dishesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<DishEntity>()
                    for (childSnapshot in snapshot.children) {
                        val dish = childSnapshot.getValue(DishEntity::class.java)
                        Log.d("FirebaseData", "Dish from Firebase: $dish")

                        dish?.let {
                            list.add(it)
                        }
                    }
                    Log.d("FirebaseData", "Total dishes fetched: ${list.size}")
                    cont.resume(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    cont.resumeWithException(error.toException())
                }
            })
        }

        dishDao.insertAll(dishesList)
    }
}
