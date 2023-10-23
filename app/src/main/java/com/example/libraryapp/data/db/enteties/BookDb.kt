package com.example.libraryapp.data.db.enteties

import androidx.room.Entity

@Entity(
    tableName = "books",
    primaryKeys = ["bookId", "bookshelf"]
)
data class BookDb(
    val bookId: String,
    val title: String,
    val authors: String,
    val publishedDate: String,
    val description: String,
    val publisher: String,
    val imageUrl: String,
    val categories: String,
    val bookshelf: String
)

//BEGIN TRANSACTION;
//ALTER TABLE books RENAME TO old_books;
//CREATE TABLE books
//(
//bookId VARCHAR NOT NULL,
//title VARCHAR NOT NULL,
//authors VARCHAR NOT NULL,
//publishedDate VARCHAR NOT NULL,
//description VARCHAR NOT NULL,
//publisher VARCHAR NOT NULL,
//imageUrl VARCHAR NOT NULL,
//categories VARCHAR NOT NULL,
//bookshelf VARCHAR NOT NULL,
//PRIMARY KEY (bookId, bookshelf)
//);
//INSERT INTO books SELECT * FROM old_books;
//DROP TABLE old_books;