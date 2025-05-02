package com.s3nko.mealplanner.utils

import android.util.Log
import com.s3nko.mealplanner.data.models.meals.MealSchedulesDTO
import com.s3nko.mealplanner.data.models.meals.MealsDTO
import com.s3nko.mealplanner.data.models.meals.WeekDTO
import com.s3nko.mealplanner.ui.models.MealSchedulesUi
import com.s3nko.mealplanner.ui.models.MealsUi
import com.s3nko.mealplanner.ui.models.WeekUi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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

fun String?.isPastDate(): Boolean {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

        val currentDate = LocalDate.now().format(outputFormatter)
        val parsedDate = this?.let { LocalDate.parse(it, inputFormatter).format(outputFormatter) } ?: ""

        parsedDate.isNotEmpty() && currentDate > parsedDate
    } catch (e: Exception) {
        Log.e("GenericExtensions", "Error parsing date: ${e.message}")
        false
    }
}