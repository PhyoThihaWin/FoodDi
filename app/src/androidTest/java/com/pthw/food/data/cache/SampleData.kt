package com.pthw.food.data.cache

import com.pthw.food.domain.model.Food

/**
 * Created by P.T.H.W on 10/08/2024.
 */
object SampleData {
    val testFoods = listOf(
        Food(
            id = 1,
            oneMM = "Delicious Apple",
            twoMM = "Juicy Orange",
            dieMM = "Fresh Banana",
            oneEN = "Apple",
            twoEN = "Orange",
            dieEN = "Banana",
            type = "fruit",
            imgOne = "http://example.com/images/apple.jpg",
            imgTwo = "http://example.com/images/orange.jpg"
        ),
        Food(
            id = 2,
            oneMM = "Crunchy Carrot",
            twoMM = "Spicy Pepper",
            dieMM = "Sweet Corn",
            oneEN = "Carrot",
            twoEN = "Pepper",
            dieEN = "Corn",
            type = "vegetable",
            imgOne = "http://example.com/images/carrot.jpg",
            imgTwo = "http://example.com/images/pepper.jpg"
        ),
        Food(
            id = 3,
            oneMM = "Savory Chicken",
            twoMM = "Juicy Tender Beef",
            dieMM = "Juicy Pork",
            oneEN = "Chicken",
            twoEN = "Beef",
            dieEN = "Pork",
            type = "meat",
            imgOne = "http://example.com/images/chicken.jpg",
            imgTwo = "http://example.com/images/beef.jpg"
        )
    )
}