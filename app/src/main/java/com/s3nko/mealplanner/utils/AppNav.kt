package com.s3nko.mealplanner.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.s3nko.mealplanner.ui.authScreens.LoginScreen
import com.s3nko.mealplanner.ui.mealsScreen.MealsScreen
import com.s3nko.mealplanner.ui.authScreens.RegisterScreen

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
                },
                navigateToRegisterScreen = {
                    navController.navigate("register_screen"){
                        popUpTo("login_screen") {
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                }
            )
        }

        composable("meals_screen"){
            MealsScreen(
                navigateToLogin = {
                    navController.navigate("login_screen"){
                        popUpTo("meals_screen") {
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                }
            )
        }

        composable("register_screen") {
            RegisterScreen(
                navigateToMealsScreen = {
                    navController.navigate("meals_screen"){
                        popUpTo("register_screen") {
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                },
                navigateToLoginScreen = {
                    navController.navigate("login_screen"){
                        popUpTo("register_screen") {
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                }
            )
        }
    }
}