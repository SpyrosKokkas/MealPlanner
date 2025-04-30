package com.s3nko.mealplanner.ui.mealsScreen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s3nko.mealplanner.R
import com.s3nko.mealplanner.ui.composables.MealsItem
import com.s3nko.mealplanner.ui.composables.RoundButtonsCompo

@Composable
fun MealsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Week",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RoundButtonsCompo(
                    R.drawable.ic_back_arrow,
                    "Back Arrow",
                    onClick = {},
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(220.dp)
                        .height(45.dp)
                        .background(Color.LightGray)
                        .clickable(onClick = { }),
                    contentAlignment = Alignment.Center
                ) {


                    Text(
                        text = "Text",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                RoundButtonsCompo(
                    R.drawable.ic_forward_arrow,
                    "Forward Arrow",
                    onClick = {
                    },
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            MealsItem()
        }
    }

}