package com.s3nko.mealplanner.ui.registerScreen

sealed class RegisterEvents {
    data class NavigateToMeals(
        val token: String,
        val userId: Int?,
        val userEmail: String?
    ) : RegisterEvents()
    data class ShowError(val message: String) : RegisterEvents()
}