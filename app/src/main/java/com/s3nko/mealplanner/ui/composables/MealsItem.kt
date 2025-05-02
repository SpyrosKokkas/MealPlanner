package com.s3nko.mealplanner.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s3nko.mealplanner.R
import com.s3nko.mealplanner.ui.models.MealsUi
import com.s3nko.mealplanner.utils.isPastDate


@Composable
fun MealsItem(
    meals: MealsUi,
    onMealLiked: (mealId: Int, isLiked: Boolean) -> Unit,
    onMealSelected: (mealId: Int, isSelected: Boolean) -> Unit,
    curDate: String?,
) {

    val description = meals.descr
    val mealName = meals.name
    val mealId = meals.id
    val isSelected = meals.isSelected
    val isLiked = meals.isLiked
    val cal = meals.cal

    val selectIsAllowed = remember { mutableStateOf(true) }
    LaunchedEffect(curDate) {
        selectIsAllowed.value = !curDate.isPastDate()
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
            .border(if (isSelected == true) 2.dp else (-1).dp, Color.Black, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .heightIn(min = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "$mealName",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(200.dp)
                )

                Spacer(modifier = Modifier.padding(24.dp))

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id =if (isLiked == true) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border),
                        contentDescription = "Like",

                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = {
                                    if (isLiked != null) {
                                        onMealLiked (mealId, isLiked)
                                    }
                                }
                            ),
                        contentScale = ContentScale.None

                    )
                }

            }

            Spacer(modifier = Modifier.padding(8.dp))

            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lunch",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            val expanded = remember { mutableStateOf(false) }

            Column {
                Text(
                    text = "$description",
                    maxLines = if (expanded.value) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp),
                )

                if (description?.length!! > 100) {
                    Text(
                        text = if (expanded.value) "Show less" else "Show more",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { expanded.value = !expanded.value }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Kcal / Serving: $cal",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.padding(8.dp))
            val backgroundColor = if (selectIsAllowed.value) Color.Gray else Color.LightGray
            val tint = if (selectIsAllowed.value) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .clickable(onClick = {
                        if (isSelected != null ){
                            onMealSelected(mealId, isSelected)
                        }
                    }),


                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSelected == true) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(
                        text = if (isSelected == true) "Unselect" else "Select",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier,
                        color = tint
                    )

                }

            }
        }
    }
}

