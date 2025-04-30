package com.s3nko.mealplanner.data.api

import com.s3nko.mealplanner.data.models.meals.WeekDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MealsApi {

    @GET("api/v1/meal_schedules/{userId}")
    suspend fun getMeals(
        @Path ("userId") userId: Int
    ): WeekDTO

    @GET("api/v1/meal_schedules/{userId}/{week}")
    suspend fun getMealsById(
        @Path ("userId") userId: Int,
        @Path ("week") week: Int
    ) : WeekDTO

}