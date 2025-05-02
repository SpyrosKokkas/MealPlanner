package com.s3nko.mealplanner.ui.registerScreen

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s3nko.mealplanner.data.repositories.auth.AuthRepository
import com.s3nko.mealplanner.network.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
): ViewModel(){

    private val _stateEvent = MutableSharedFlow<RegisterEvents?>()
    val stateEvents: SharedFlow<RegisterEvents?> = _stateEvent


    fun register(password: String, username: String , passwordVer: String) {
        println(password)
        println(username)
        val empty = checkFields(email = username , password = password)
        val emailFormat = checkEmailFormat(email = username)
        val passwordFormat = checkPasswordFormat(password = password)
        val passwordsMatch = passwordsMatch(password = password , passwordVer = passwordVer)

        viewModelScope.launch {

            if(empty && emailFormat && passwordFormat && passwordsMatch) {
                try {
                    val registerResult = authRepository.registerUser(username = username, password = password , passwordVer = passwordVer)

                    if (registerResult?.token != null) {

                        tokenManager.saveToken(registerResult.token)
                        registerResult.userId?.let { tokenManager.saveUserId(it) }

                        _stateEvent.emit(
                            RegisterEvents.NavigateToMeals(
                                token = registerResult.token,
                                userId = registerResult.userId,
                                userEmail = registerResult.userEmail
                            )
                        )
                    }
                } catch (e: Exception) {
                    _stateEvent.emit(
                        RegisterEvents.ShowError("Login failed: ${e.message}")
                    )
                }
            } else {
                if (!empty) {
                    _stateEvent.emit(RegisterEvents.ShowError("Παρακαλώ συμπληρώστε όλα τα πεδία"))
                }else if(!emailFormat) {
                    _stateEvent.emit(RegisterEvents.ShowError("Παρακαλώ συμπληρώστε ενα email"))
                }else if(!passwordFormat) {
                    _stateEvent.emit(RegisterEvents.ShowError("Ο κωδικός πρέπει να περιέχει πάνω απο 6 ψηφία"))
                }else if(!passwordsMatch) {
                    _stateEvent.emit(RegisterEvents.ShowError("Οι κωδικοί δεν ταιριάζουν"))
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
        return password.length >= 6
    }

    private fun passwordsMatch(password: String , passwordVer: String): Boolean {
        return password == passwordVer
    }
}