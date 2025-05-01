package com.s3nko.mealplanner.data.api

import com.s3nko.mealplanner.data.models.choices.ChoiceUpdate
import com.s3nko.mealplanner.data.models.meals.WeekDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
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

    @HTTP(method = "DELETE", path = "api/v1/selections", hasBody = true)
    suspend fun deleteSelection(
        @Body request: ChoiceUpdate
    ): WeekDTO

    @POST("api/v1/selections")
    suspend fun updateSelection(
        @Body request: ChoiceUpdate
    ): WeekDTO


    @POST("api/v1/preferences")
    suspend fun addLike(
        @Body request: ChoiceUpdate
    ): WeekDTO

    @HTTP(method = "DELETE", path = "api/v1/preferences", hasBody = true)
    suspend fun deleteLike(
        @Body request: ChoiceUpdate
    ): WeekDTO

}