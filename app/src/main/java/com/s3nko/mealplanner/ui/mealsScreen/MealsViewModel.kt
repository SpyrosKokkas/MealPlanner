package com.s3nko.mealplanner.ui.mealsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s3nko.mealplanner.data.repositories.meals.MealsRepository
import com.s3nko.mealplanner.network.TokenManager
import com.s3nko.mealplanner.ui.models.WeekUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    private val userId = tokenManager.getUserId()

    private var _state = MutableStateFlow<WeekUi?>(null)
    var state: StateFlow<WeekUi?> = _state

    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin: SharedFlow<Unit> = _navigateToLogin

    init {
        fetchAllData(userId)
    }

    private fun fetchAllData(userId : Int , weekId: Int? = null) {

        viewModelScope.launch {
            try {
                if (weekId == null) {
                   val result =  mealsRepository.getWeeklyMeals(userId = userId)
                    _state.value = result
                } else {
                    val result = mealsRepository.getWeeklyMeals(userId = userId, weekId = weekId)
                    _state.value = result
                }


            } catch (e: Exception) {
                // Exception handling
                Log.e("MealsViewModel", "Error fetching meals: ${e.message}")
                if (e.message == "HTTP 401 Unauthorized") {
                    // Handle unauthorized error
                    Log.e("MealsViewModel", "Unauthorized access: ${e.message}")
                    viewModelScope.launch {
                        _navigateToLogin.emit(Unit)
                    }
                }
            }
        }
    }

    fun fetchWeeklyMeals(weekId: Int) {
        fetchAllData(userId, weekId)
    }

    fun selection(mealId: Int , isSelected: Boolean){
        viewModelScope.launch {
            try {
                mealsRepository.updateMealSelection(mealId = mealId , isSelected = isSelected)
                fetchAllData(userId)
            } catch (e: Exception) {
                Log.e("MealsViewModel", "Error updating meal selection: ${e.message}")
            }
        }
    }

    fun like (mealId: Int , isLiked: Boolean){
        viewModelScope.launch {
            try {
                mealsRepository.updateMealLike(mealId = mealId , isLiked = isLiked)
                fetchAllData(userId)
            } catch (e: Exception) {
                Log.e("MealsViewModel", "Error updating meal like: ${e.message}")
            }
        }
    }

}