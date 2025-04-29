package com.s3nko.mealplanner.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.s3nko.mealplanner.ui.loginScreen.LoginScreen
import com.s3nko.mealplanner.ui.mealsScreen.MealsScreen

@Composable
fun AppNav() {
    val navController =  rememberNavController()

    NavHost(
        navController= navController,
        startDestination = "login_screen"
    ) {
        composable("login_screen") {
            LoginScreen(
                navigateToMealsScreen= {
                    navController.navigate("meals_screen"){
                        popUpTo("login_screen") {
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                }
            )
        }

        composable("meals_screen"){
            MealsScreen()
        }
    }
}