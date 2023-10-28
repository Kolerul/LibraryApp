package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getAllBooksByTitleAndAuthor(title: String, author: String): List<Book>

    fun getAllBooksFromBookshelf(bookshelf: Bookshelf): Flow<List<Book>>

    suspend fun getBookById(id: String, bookshelf: Bookshelf): Book

    suspend fun addBookToBookshelf(book: Book, bookshelf: Bookshelf)

    suspend fun deleteBookFromBookshelf(id: String, bookshelf: Bookshelf)
}