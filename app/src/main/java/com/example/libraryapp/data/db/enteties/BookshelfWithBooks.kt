package com.example.libraryapp.data.db.enteties

import androidx.room.Embedded
import androidx.room.Relation

data class BookshelfWithBooks(
    @Embedded val bookshelf: BookshelfDb,
    @Relation(
        parentColumn = "bookshelfTitle",
        entityColumn = "bookshelf"
    )
    val bookList: List<BookDb>
)