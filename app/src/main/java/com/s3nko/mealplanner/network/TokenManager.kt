package com.s3nko.mealplanner.network

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
    }

    fun saveToken(token: String) {
        val currentToken = getToken()

        Log.d("Token Manager", "Current Token: $currentToken, Incoming Token: $token")

        if (currentToken != token) {
            sharedPreferences.edit() { putString(KEY_TOKEN, token) }
            Log.d("Token Manager", "Token Saved: $token")
        } else {
            Log.d("Token Manager", "Token already exists, skipping save: $currentToken")
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)

    }

    fun saveUserId(userId: Int) {
        sharedPreferences.edit() { putInt(KEY_USER_ID, userId) }
    }

    fun getUserId(): Int {
        val userId = sharedPreferences.getInt(KEY_USER_ID, -1)
        return if (userId == -1) -1 else userId
    }

    fun clearToken() {
        sharedPreferences.edit() { remove(KEY_TOKEN).remove(KEY_USER_ID).remove(KEY_USER_EMAIL) }
    }
}