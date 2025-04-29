package com.s3nko.mealplanner.data.models.authentication

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("role") val role: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("jti") val jti: String? = null
)
