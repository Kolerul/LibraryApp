package com.example.libraryapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.enteties.BookDb

@Database(
    entities = [BookDb::class],
    version = 1
)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

}