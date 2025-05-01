package com.s3nko.mealplanner.ui.models

data class MealsUi(
    val id : Int,
    val name: String? = null,
    val descr : String? = null,
    val cal: Int? = null,
    val isLiked: Boolean? = null,
    val isSelected: Boolean? = null,
)
