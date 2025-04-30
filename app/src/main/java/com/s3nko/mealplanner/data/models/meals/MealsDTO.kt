package com.s3nko.mealplanner.data.models.meals

import com.google.gson.annotations.SerializedName

data class MealsDTO(
    @SerializedName("id") var mealId: Int,
    @SerializedName("name") var mealName: String? = null,
    @SerializedName("description") var mealDescr: String? = null,
    @SerializedName("calories") var mealCal: Int? = null,
    @SerializedName("is_liked") var isLiked: Boolean? = null,
    @SerializedName("is_selected") var isSelected: Boolean? = null
)
