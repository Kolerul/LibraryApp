package com.example.libraryapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookshelves"
)
data class BookshelfDb(
    @PrimaryKey(autoGenerate = false)
    val bookshelfTitle: String
)