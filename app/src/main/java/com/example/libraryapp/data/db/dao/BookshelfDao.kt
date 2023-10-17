package com.example.libraryapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.libraryapp.data.db.enteties.BookshelfDb

@Dao
interface BookshelfDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookshelf(bookshelf: BookshelfDb)

    @Delete
    fun deleteBookshelf(bookshelf: BookshelfDb)

    @Query("SELECT * FROM bookshelfs")
    fun getAllBookshelves(): List<BookshelfDb>
}