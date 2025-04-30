package com.s3nko.mealplanner.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RoundButtonsCompo(@DrawableRes iconRes: Int,
                      contentDescription: String?,
                      onClick: () -> Unit,
) {
    Box(

        modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable(onClick = onClick ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center)
        )
    }
}