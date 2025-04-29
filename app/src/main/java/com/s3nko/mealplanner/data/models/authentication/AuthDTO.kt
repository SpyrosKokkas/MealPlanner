package com.s3nko.mealplanner.data.models.authentication

import com.google.gson.annotations.SerializedName

data class AuthDTO(
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: UserDTO? = null
)
