package com.example.libraryapp.data.db.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookshelfs"
)
data class BookshelfDb(
    @PrimaryKey(autoGenerate = false)
    val bookshelfTitle: String
)