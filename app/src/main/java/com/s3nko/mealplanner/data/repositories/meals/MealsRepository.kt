package com.s3nko.mealplanner.data.repositories.meals

import androidx.lifecycle.ViewModel
import com.s3nko.mealplanner.data.api.MealsApi
import com.s3nko.mealplanner.ui.models.WeekUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.s3nko.mealplanner.utils.toUi

class MealsRepository @Inject constructor(
    private val mealsApi: MealsApi
): ViewModel() {

    // Fetches the weekly meals from the api
    // If the weekId is null then the request goes without a weekId and i get back the default/current week
    suspend fun getWeeklyMeals(weekId: Int? = null, userId: Int) : WeekUi{
        return withContext(Dispatchers.IO) {
            val response = if (weekId == null) {
                mealsApi.getMeals(userId)
            } else {
                mealsApi.getMealsById(userId = userId, week = weekId)
            }

            response.toUi()
        }
    }

}