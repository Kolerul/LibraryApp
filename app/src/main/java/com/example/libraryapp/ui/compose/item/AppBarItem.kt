package com.example.libraryapp.ui.compose.item

import androidx.compose.ui.graphics.vector.ImageVector

data class AppBarItem(
    val image: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)