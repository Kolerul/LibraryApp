package com.example.libraryapp.data.db.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "books"
)
data class BookDb(
    @PrimaryKey(autoGenerate = true)
    val bookId: Long = 0,
    val title: String,
    val authors: String,
    val googleId: String,
    val publishedDate: String,
    val description: String,
    val publisher: String,
    val imageUrl: String,
    val categories: String,
    val bookshelf: String
)