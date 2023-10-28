package com.example.libraryapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.libraryapp.data.db.model.BookDb

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookDb)

    @Query("DELETE FROM books WHERE bookId = :id AND bookshelf = :bookshelf")
    fun deleteBookFromBookshelfById(id: String, bookshelf: String)

    @Update
    fun updateBook(book: BookDb)

    @Query("SELECT * FROM books WHERE bookId = :id LIMIT 1")
    fun getBook(id: String): BookDb

    @Query("SELECT * FROM books ORDER BY title")
    fun getAllBooks(): List<BookDb>

    @Query("SELECT * FROM books WHERE bookshelf = :bookshelf ORDER BY title")
    fun getAllBooksFromBookshelf(bookshelf: String): List<BookDb>

}