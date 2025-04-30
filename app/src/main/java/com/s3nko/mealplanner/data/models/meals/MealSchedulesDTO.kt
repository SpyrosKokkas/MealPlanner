package com.s3nko.mealplanner.data.models.meals

import com.google.gson.annotations.SerializedName

data class MealSchedulesDTO(
    @SerializedName("date") var date: String? = null,
    @SerializedName("meals") var meals: List<MealsDTO>? = null
)
