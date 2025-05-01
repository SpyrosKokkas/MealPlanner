package com.s3nko.mealplanner.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s3nko.mealplanner.ui.models.MealSchedulesUi

@Composable
fun MealsPager(
    weekId : Int?,
    mealsWeek: List<MealSchedulesUi>?,
    onMealLiked: (mealId: Int, isLiked: Boolean) -> Unit,
    onMealSelected: (mealId: Int, isSelected: Boolean) -> Unit
){
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    val listState = rememberLazyListState()


    LaunchedEffect(weekId) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues( vertical = 16.dp)
    ) {

        mealsWeek?.forEachIndexed { index, mealSchedulesUi ->
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${daysOfWeek.getOrNull(index)} ${mealSchedulesUi.date}",
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    val meals = mealSchedulesUi.meals ?: emptyList()
                    val selectedMeal = meals.find { it.isSelected == true }

                    if (selectedMeal != null) {
                        MealsItem(
                            meals = selectedMeal,
                            onMealLiked = { mealId, isLiked ->
                                onMealLiked(mealId, isLiked)
                            },
                            onMealSelected = { mealId, isSelected ->
                                onMealSelected(mealId, isSelected)
                            },
                        )
                    } else {
                        if (meals.isNotEmpty()) {

                            val pagerState = rememberPagerState(
                                initialPage = 0,
                                initialPageOffsetFraction = 0.05F,
                                pageCount = { meals.size }
                            )

                            HorizontalPager(
                                state = pagerState,
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) { page ->
                                val meal = meals[page]
                                MealsItem(
                                    meals = meal,
                                    onMealLiked = { mealId, isLiked ->
                                        onMealLiked(mealId, isLiked)
                                    },
                                    onMealSelected = { mealId, isSelected ->
                                        onMealSelected(mealId, isSelected)
                                    }
                                )
                            }
                        }else {
                            Text(
                                text = "No meals available for this day",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                }
            }
        }


    }

}