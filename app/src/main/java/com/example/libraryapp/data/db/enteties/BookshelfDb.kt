package com.example.libraryapp.data.db.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookshelfDb(
    @PrimaryKey(autoGenerate = true)
    val bookshelfId: Long = 0,
    val bookshelfTitle: String
)