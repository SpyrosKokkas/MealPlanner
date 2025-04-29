package com.s3nko.mealplanner.data.models.authentication

data class AuthResult(
    val message: String?= null,
    val token: String? = null,
    val userId: Int? = null,
    val userEmail: String? = null
)

