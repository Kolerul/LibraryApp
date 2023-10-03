package com.example.libraryapp.ui.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    secondary = Green,
    tertiary = Blue
)

@Composable
fun TestTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}