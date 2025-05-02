package com.s3nko.mealplanner.data.repositories.auth

import androidx.lifecycle.ViewModel
import com.s3nko.mealplanner.data.api.AuthApi
import com.s3nko.mealplanner.data.models.authentication.AuthRequest
import com.s3nko.mealplanner.data.models.authentication.AuthRequestRegister
import com.s3nko.mealplanner.data.models.authentication.AuthResult
import com.s3nko.mealplanner.data.models.authentication.UserLogin
import com.s3nko.mealplanner.data.models.authentication.UserRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
   private val authApi: AuthApi
): ViewModel() {

    // Receives the user email and password and tries to log in
    suspend fun loginUser(username: String, password: String): AuthResult? {
        return withContext(Dispatchers.IO) {
            try {
                val request = AuthRequest(
                    user = UserLogin(
                        email = username,
                        password = password
                    )
                )
                // calls the api to login
                val response = authApi.userLogin(request)
                AuthResult(
                    token = response.token,
                    userId = response.user?.id,
                    userEmail = response.user?.email,
                    message = response.message
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun registerUser(username: String, password: String, passwordVer: String): AuthResult? {
        return withContext(Dispatchers.IO) {
            try {
                val request = AuthRequestRegister(
                    user = UserRegister(
                        email = username,
                        password = password,
                        password_confirmation = passwordVer
                    )
                )
                // calls the api to login
                val response = authApi.userRegister(request)
                AuthResult(
                    token = response.token,
                    userId = response.user?.id,
                    userEmail = response.user?.email,
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}