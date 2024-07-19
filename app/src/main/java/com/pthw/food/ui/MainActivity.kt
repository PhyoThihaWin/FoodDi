package com.pthw.food.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pthw.food.theme.FoodDiAppTheme
import com.pthw.food.ui.main.HomePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDiAppTheme {
                HomePage()
            }
        }
    }
}