package com.s3nko.mealplanner.data.api

import com.s3nko.mealplanner.data.models.authentication.AuthDTO
import com.s3nko.mealplanner.data.models.authentication.AuthRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    //Post request for login
    @POST("api/v1/login")
    suspend fun userLogin (
       @Body request : AuthRequest
    ) : AuthDTO

}