package com.s3nko.mealplanner.ui.mealsScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.s3nko.mealplanner.R
import com.s3nko.mealplanner.ui.composables.MealsPager
import com.s3nko.mealplanner.ui.composables.RoundButtonsCompo

@Composable
fun MealsScreen(
    viewModel: MealsViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {

    val state = viewModel.state.collectAsState()
    val weekRange = state.value?.weekRange
    val maxWeek = state.value?.maxWeekId
    val weekId = state.value?.weekId
    val mealSchedules = state.value?.mealSchedules
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect { s ->
            if (s == 1) {
                Toast.makeText(context , "Session expired. Please login again!", Toast.LENGTH_LONG).show()
                navigateToLogin()
            } else if (s == 0 ){
                Toast.makeText(context,"Logged out successfully", Toast.LENGTH_LONG).show()
                navigateToLogin()
            }
        }
    }




    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Select Week",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = {
                        viewModel.clearToken(0)
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Logout",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val enabledBack = weekId != 1
                RoundButtonsCompo(
                    R.drawable.ic_back_arrow,
                    "Back Arrow",
                    onClick = {
                        if (weekId != null) {
                            viewModel.fetchWeeklyMeals(weekId = weekId.minus(1))
                        }
                    },
                    enabled = enabledBack
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(220.dp)
                        .height(45.dp)
                        .background(Color.LightGray)
                        .clickable(onClick = { })
                        .border(2.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    val range = if (weekRange.isNullOrEmpty()) {
                        ""
                    } else {
                        weekRange
                    }
                    Text(
                        text = range,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                val enabled = weekId != maxWeek
                RoundButtonsCompo(
                    R.drawable.ic_forward_arrow,
                    "Forward Arrow",
                    onClick = {
                        if (weekId != null && maxWeek != null) {
                            viewModel.fetchWeeklyMeals(weekId.plus(1))
                        }
                    },
                    enabled = enabled
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            MealsPager(
                weekId = weekId,
                mealsWeek = mealSchedules,
                onMealSelected = { mealId, isSelected ->
                    viewModel.selection(mealId, isSelected)

                },
                onMealLiked = { mealId, isLiked ->
                    viewModel.like(mealId = mealId , isLiked = isLiked)
                }
            )
        }
    }
}