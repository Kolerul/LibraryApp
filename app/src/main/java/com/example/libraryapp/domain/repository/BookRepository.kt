package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.entity.Book

interface BookRepository {
    suspend fun getAllBooksByTitleAndAuthor(title: String, author: String): List<Book>

    suspend fun getAllBooksFromBookshelf(bookshelf: String): List<Book>

    suspend fun getBookById(id: String, bookshelf: String): Book

    suspend fun addBookToBookshelf(book: Book, bookshelf: String)

    suspend fun deleteBookFromBookshelf(id: String, bookshelf: String)
}