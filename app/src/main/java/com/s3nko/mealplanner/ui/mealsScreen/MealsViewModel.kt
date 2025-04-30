package com.s3nko.mealplanner.ui.mealsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s3nko.mealplanner.data.repositories.meals.MealsRepository
import com.s3nko.mealplanner.ui.models.WeekUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository
): ViewModel() {

    private var _state = MutableStateFlow<WeekUi?>(null)
    var state: StateFlow<WeekUi?> = _state

    init {
        fetchAllData()
    }

    private fun fetchAllData() {

        viewModelScope.launch {
            try {
                // hard coded userId and week for testing
                val result = mealsRepository.getWeeklyMeals(userId = 1 , weekId = 13)
                _state.value = result

            } catch (e: Exception) {
                // Διαχείριση σφάλματος
                Log.e("MealsViewModel", "Error fetching meals: ${e.message}")
            }
        }
    }

}