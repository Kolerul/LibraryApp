package com.example.libraryapp.ui.model

import androidx.annotation.DrawableRes


data class MenuItem(
    @DrawableRes val iconId: Int,
    val title: String,
    val onClick: () -> Unit
)