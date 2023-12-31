package com.example.libraryapp.di

import android.content.Context
import androidx.room.Room
import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.db.database.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideBookDatabase(context: Context): BookDatabase =
        Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "Book database"
        ).addMigrations(BookDatabase.compositePrimaryKeyMigration).build()

    @Singleton
    @Provides
    fun provideBookDao(db: BookDatabase): BookDao = db.getBookDao()

    @Singleton
    @Provides
    fun provideBookshelfDao(db: BookDatabase): BookshelfDao = db.getBookshelfDao()
}