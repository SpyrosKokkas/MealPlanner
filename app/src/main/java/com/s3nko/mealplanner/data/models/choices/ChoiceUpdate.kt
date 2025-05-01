package com.s3nko.mealplanner.data.models.choices

import com.google.gson.annotations.SerializedName

data class ChoiceUpdate(
    @SerializedName("meal_schedule_id") val mealScheduleId: Int,
    @SerializedName("user_id") val userId: Int,
)
