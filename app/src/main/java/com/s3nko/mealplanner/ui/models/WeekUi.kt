package com.s3nko.mealplanner.ui.models

data class WeekUi(
    val weekId : Int?,
    val weekRange: String?,
    val maxWeekId: Int?,
    val mealSchedules: List<MealSchedulesUi>?,
)
