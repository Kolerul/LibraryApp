package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.entity.Bookshelf

interface BookshelfRepository {
    suspend fun createBookshelf(title: String)

    suspend fun deleteBookshelf(bookshelf: Bookshelf)

    suspend fun getAllBookshelves(): List<Bookshelf>

    suspend fun getAllBookshelvesWithNumberOfBooks(): List<Pair<Bookshelf, Int>>
}