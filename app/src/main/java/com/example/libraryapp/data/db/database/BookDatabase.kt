package com.example.libraryapp.data.db.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.db.enteties.BookDb
import com.example.libraryapp.data.db.enteties.BookshelfDb

@Database(
    entities = [
        BookDb::class,
        BookshelfDb::class
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = BookDatabase.BookIdChangeAutoMigration::class
        ),
        AutoMigration(
            from = 2,
            to = 3
        )],
    exportSchema = true
)
abstract class BookDatabase: RoomDatabase() {

    @DeleteColumn(tableName = "books", columnName = "bookId")
    @RenameColumn(tableName = "books", fromColumnName = "googleId", toColumnName = "bookId")
    class BookIdChangeAutoMigration : AutoMigrationSpec

    abstract fun getBookDao(): BookDao

    abstract fun getBookshelfDao(): BookshelfDao

}