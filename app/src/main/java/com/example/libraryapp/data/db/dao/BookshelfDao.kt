package com.example.libraryapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.libraryapp.data.db.model.BookshelfDb
import com.example.libraryapp.data.db.model.BookshelfWithBooks
import kotlinx.coroutines.flow.Flow

@Dao
interface BookshelfDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookshelf(bookshelf: BookshelfDb)

    @Delete
    fun deleteBookshelf(bookshelf: BookshelfDb)

    @Query("SELECT * FROM bookshelves")
    fun getAllBookshelves(): List<BookshelfDb>

    @Transaction
    @Query("SELECT * from bookshelves")
    fun getAllBookshelvesWithBooks(): Flow<List<BookshelfWithBooks>>
}
