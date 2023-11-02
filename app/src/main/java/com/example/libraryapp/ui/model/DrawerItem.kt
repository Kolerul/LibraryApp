package com.example.libraryapp.ui.model

import com.example.libraryapp.domain.model.Bookshelf

data class DrawerItem(
    val number: Int,
    val bookshelf: Bookshelf,
    val count: Int
)