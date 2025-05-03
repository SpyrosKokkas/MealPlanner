package com.s3nko.mealplanner.ui.authScreens

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s3nko.mealplanner.data.repositories.auth.AuthRepository
import com.s3nko.mealplanner.network.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _authEvents = MutableStateFlow<AuthEvents?>(null)
    val authEvents: StateFlow<AuthEvents?> = _authEvents

    //Checks if a token is available to skip the login screen
    init {
        val cToken = tokenManager.getToken()
        val cUserId = tokenManager.getUserId()
        Log.d(("AuthViewModel"), "Current token: $cToken")
        if (cToken != null) {
            viewModelScope.launch {
                Log.d("AuthViewModel", "Token is not null, navigating to meals screen")
                _authEvents.emit(AuthEvents.NavigateToMeals(token = cToken, userId = cUserId))
            }
        }
    }

    fun login(password: String, username: String) {
        val empty = checkFields(email = username, password = password)
        val emailFormat = checkEmailFormat(email = username)
        val passwordFormat = checkPasswordFormat(password = password)

        viewModelScope.launch {
            if (empty && emailFormat && passwordFormat) {
                try {
                    val loginResult = authRepository.loginUser(username, password)
                    if (loginResult?.token != null) {
                        tokenManager.saveToken(loginResult.token)
                        loginResult.userId?.let { tokenManager.saveUserId(it) }

                        _authEvents.emit(
                            AuthEvents.NavigateToMeals(
                                token = loginResult.token,
                                userId = loginResult.userId,
                                userEmail = loginResult.userEmail // can be null
                            )
                        )
                    } else {
                        _authEvents.emit(AuthEvents.ShowError("Wrong credentials"))
                    }
                } catch (e: Exception) {
                    _authEvents.emit(AuthEvents.ShowError("Login failed: ${e.message}"))
                }
            } else {
                if (!empty) {
                    _authEvents.emit(AuthEvents.ShowError("Please fill in all fields"))
                }else if(!emailFormat) {
                    _authEvents.emit(AuthEvents.ShowError("Please enter a valid email"))
                }else {
                    _authEvents.emit(AuthEvents.ShowError("Password must be at least 6 characters"))
                }
            }
        }
    }


    fun register(password: String, username: String, passwordVer: String) {
        val empty = checkFields(email = username, password = password)
        val emailFormat = checkEmailFormat(email = username)
        val passwordFormat = checkPasswordFormat(password = password)
        val passwordsMatch = passwordsMatch(password, passwordVer)

        viewModelScope.launch {
            if (empty && emailFormat && passwordFormat && passwordsMatch) {
                try {
                    val registerResult = authRepository.registerUser(username, password, passwordVer)
                    if (registerResult?.token != null) {
                        tokenManager.saveToken(registerResult.token)
                        registerResult.userId?.let { tokenManager.saveUserId(it) }

                        _authEvents.emit(
                            AuthEvents.NavigateToMeals(
                                token = registerResult.token,
                                userId = registerResult.userId,
                                userEmail = registerResult.userEmail
                            )
                        )
                    }
                } catch (e: Exception) {
                    _authEvents.emit(AuthEvents.ShowError("Registration failed: ${e.message}"))
                }
            } else {
                if (!empty) {
                    _authEvents.emit(AuthEvents.ShowError("Παρακαλώ συμπληρώστε όλα τα πεδία"))
                }else if(!emailFormat) {
                    _authEvents.emit(AuthEvents.ShowError("Παρακαλώ συμπληρώστε ενα email"))
                }else if(!passwordFormat) {
                    _authEvents.emit(AuthEvents.ShowError("Ο κωδικός πρέπει να περιέχει πάνω απο 6 ψηφία"))
                }else {
                    _authEvents.emit(AuthEvents.ShowError("Οι κωδικοί δεν ταιριάζουν"))
                }
            }
        }
    }


    private fun checkFields(email : String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun checkEmailFormat(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPasswordFormat(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    private fun passwordsMatch(password: String , passwordVer: String): Boolean {
        return password == passwordVer
    }
}