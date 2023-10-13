package com.example.libraryapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.db.enteties.BookDb
import com.example.libraryapp.data.db.enteties.BookshelfDb

@Database(
    entities = [
        BookDb::class,
        BookshelfDb::class
    ],
    version = 2
)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

    abstract fun getBookshelfDao(): BookshelfDao

}