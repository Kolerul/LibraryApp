package com.example.libraryapp.ui.util

import com.example.libraryapp.R

fun randomizeBookImage(): Int {
    val random = Math.random() * 7
    if (random < 1) return R.drawable.red_book
    if (random < 2) return R.drawable.blue_book
    if (random < 3) return R.drawable.green_book
    if (random < 4) return R.drawable.yellow_book
    if (random < 5) return R.drawable.orange_book
    if (random < 6) return R.drawable.danvich
    return R.drawable.violet_book
}