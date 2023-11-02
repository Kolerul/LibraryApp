package com.example.libraryapp.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class AppBarItem(
    val image: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)