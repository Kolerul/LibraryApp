package com.example.libraryapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.libraryapp.data.db.enteties.BookDb

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookDb)

    @Delete
    fun deleteBook(book: BookDb)

    @Update
    fun updateBook(book: BookDb)

    @Query("SELECT * FROM books WHERE title = :title AND bookshelf = :bookshelf")
    fun getBookFromBookshelfByTitle(title: String, bookshelf: String): BookDb

    @Query("SELECT * FROM books ORDER BY title")
    fun getAllBooks(): List<BookDb>

    @Query("SELECT * FROM books WHERE bookshelf = :bookshelf ORDER BY title")
    fun getAllBooksFromBookshelf(bookshelf: String): List<BookDb>

}