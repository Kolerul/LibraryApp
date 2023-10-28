package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.Bookshelf
import kotlinx.coroutines.flow.Flow

interface BookshelfRepository {
    suspend fun createBookshelf(title: String)

    suspend fun deleteBookshelf(bookshelf: Bookshelf)

    suspend fun getAllBookshelves(): List<Bookshelf>

    fun getAllBookshelvesWithNumberOfBooks(): Flow<List<Pair<Bookshelf, Int>>>
}