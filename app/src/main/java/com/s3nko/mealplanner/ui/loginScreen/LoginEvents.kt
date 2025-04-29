package com.s3nko.mealplanner.ui.loginScreen

sealed class LoginEvents {
    data class NavigateToMeals(
        val token: String,
        val userId: Int?,
        val userEmail: String?
    ) : LoginEvents()
    data class ShowError(val message: String) : LoginEvents()

}