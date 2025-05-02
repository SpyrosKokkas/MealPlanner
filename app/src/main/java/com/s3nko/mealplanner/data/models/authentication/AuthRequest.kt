package com.s3nko.mealplanner.data.models.authentication

data class AuthRequest(
    val user: UserLogin
)

data class UserLogin(
    val email: String,
    val password: String
)

data class AuthRequestRegister(
    val user: UserRegister
)

data class UserRegister(
    val email: String,
    val password: String,
    val password_confirmation: String
)