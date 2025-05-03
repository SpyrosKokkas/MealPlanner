package com.s3nko.mealplanner.ui.authScreens

sealed class AuthEvents {
    data class NavigateToMeals(
        val token: String,
        val userId: Int?,
        val userEmail: String? = null
    ) : AuthEvents()

    data class ShowError(val message: String) : AuthEvents()
}