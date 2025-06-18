package com.example.georgiandishes.MainPage.HomePage.Database

data class Dish(
    val id: Int ,
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val imageUrl: String = "",
    val region: String = ""
)
fun Dish.toEntity(): DishEntity {
    return DishEntity(
        id = id,
        name = name,
        description = description,
        recipe = recipe,
        imageUrl = imageUrl,
        region = region
    )
}

fun DishEntity.toDish(): Dish {
    return Dish(
        id = id,
        name = name,
        description = description,
        recipe = recipe,
        imageUrl = imageUrl,
        region = region
    )
}
