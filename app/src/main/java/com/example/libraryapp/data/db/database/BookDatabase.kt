package com.example.libraryapp.data.db.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.db.model.BookDb
import com.example.libraryapp.data.db.model.BookshelfDb

@Database(
    entities = [
        BookDb::class,
        BookshelfDb::class
    ],
    version = 5,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = BookDatabase.BookIdChangeAutoMigration::class
        ),
        AutoMigration(
            from = 2,
            to = 3
        ),
        AutoMigration(
            from = 3,
            to = 4,
            spec = BookDatabase.BookshelvesChangeAutoMigration::class
        )
    ],
    exportSchema = true
)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

    abstract fun getBookshelfDao(): BookshelfDao

    @DeleteColumn(tableName = "books", columnName = "bookId")
    @RenameColumn(tableName = "books", fromColumnName = "googleId", toColumnName = "bookId")
    class BookIdChangeAutoMigration : AutoMigrationSpec

    @RenameTable(fromTableName = "bookshelfs", toTableName = "bookshelves")
    class BookshelvesChangeAutoMigration : AutoMigrationSpec

    companion object {
        val compositePrimaryKeyMigration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE new_books (bookId TEXT NOT NULL, title TEXT NOT NULL, authors TEXT NOT NULL, publishedDate TEXT NOT NULL, description TEXT NOT NULL, publisher TEXT NOT NULL, imageUrl TEXT NOT NULL, categories TEXT NOT NULL, bookshelf TEXT NOT NULL, PRIMARY KEY (bookId, bookshelf))")

                database.execSQL("INSERT INTO new_books (bookId, title, authors, publishedDate, description, publisher, imageUrl, categories, bookshelf) SELECT bookId, title, authors, publishedDate, description, publisher, imageUrl, categories, bookshelf FROM books")

                database.execSQL("DROP TABLE books")

                database.execSQL("ALTER TABLE new_books RENAME TO books")

                database.execSQL("INSERT INTO bookshelves (bookshelfTitle) VALUES (Favourite)")
            }
        }
    }
}