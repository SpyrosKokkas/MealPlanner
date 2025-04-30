package com.s3nko.mealplanner.data.models.meals

import com.google.gson.annotations.SerializedName

data class WeekDTO(
    @SerializedName("week_id") var weekId: Int? = null,
    @SerializedName("week_range") var dateRange: String? = null,
    @SerializedName("last_week_id") var maxWeek: Int? = null,
    @SerializedName("meal_schedules") var mealSchedules: List<MealSchedulesDTO>? = null
)
