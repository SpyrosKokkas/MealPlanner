package com.s3nko.mealplanner.ui.loginScreen

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s3nko.mealplanner.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _stateEvent = MutableSharedFlow<LoginEvents?>()
    val stateEvents: SharedFlow<LoginEvents?> = _stateEvent

    fun login(password: String, username: String) {

        val empty = checkFields(email = username , password = password)
        val emailFormat = checkEmailFormat(email = username)
        val passwordFormat = checkPasswordFormat(password = password)

        viewModelScope.launch {

            if(empty && emailFormat && passwordFormat) {
                try {
                    val loginResult = authRepository.loginUser(username = username, password = password)
                    if (loginResult?.token != null) {

                        _stateEvent.emit(
                            LoginEvents.NavigateToMeals(
                                token = loginResult.token,
                                userId = loginResult.userId,
                                userEmail = loginResult.userEmail
                            )
                        )

                       // TODO("Save token and user details")
                    }else {
                        _stateEvent.emit(LoginEvents.ShowError("Wrong credentials"))
                    }

                } catch (e: Exception) {
                    _stateEvent.emit(
                        LoginEvents.ShowError("Login failed: ${e.message}")
                    )
                }
            } else {
                if (!empty) {
                    _stateEvent.emit(LoginEvents.ShowError("Please fill in all fields"))
                }else if(!emailFormat) {
                    _stateEvent.emit(LoginEvents.ShowError("Please enter a valid email"))
                }else if(!passwordFormat) {
                    _stateEvent.emit(LoginEvents.ShowError("Password must be at least 6 characters"))
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
}