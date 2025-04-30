package com.s3nko.mealplanner.utils

import com.s3nko.mealplanner.data.models.meals.MealSchedulesDTO
import com.s3nko.mealplanner.data.models.meals.MealsDTO
import com.s3nko.mealplanner.data.models.meals.WeekDTO
import com.s3nko.mealplanner.ui.models.MealSchedulesUi
import com.s3nko.mealplanner.ui.models.MealsUi
import com.s3nko.mealplanner.ui.models.WeekUi


fun WeekDTO.toUi() : WeekUi {
    return WeekUi(
            weekId = this.weekId,
            weekRange = this.dateRange,
            maxWeekId = this.maxWeek,
            mealSchedules= this.mealSchedules?.map { it.toUi() } )
    }


fun MealSchedulesDTO.toUi(): MealSchedulesUi {
    return MealSchedulesUi(
        date = this.date,
        meals = this.meals?.map { it.toUi() }
    )
}


fun MealsDTO.toUi(): MealsUi {
    return MealsUi(
        id = this.mealId,
        name = this.mealName,
        descr = this.mealDescr,
        cal = this.mealCal,
        isLiked = this.isLiked,
        isSelected = this.isSelected
    )
}