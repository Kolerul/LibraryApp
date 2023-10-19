package com.example.libraryapp.ui.compose.item

import com.example.libraryapp.domain.entity.Bookshelf

data class DrawerItem(
    val number: Int,
    val bookshelf: Bookshelf
)