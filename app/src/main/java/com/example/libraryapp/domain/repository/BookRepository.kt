package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.domain.entity.Bookshelf

interface BookRepository {
    suspend fun getAllBooksByTitleAndAuthor(title: String, author: String): List<Book>

    suspend fun getAllBooksFromBookshelf(bookshelf: Bookshelf): List<Book>

    suspend fun getBookById(id: String, bookshelf: Bookshelf): Book

    suspend fun addBookToBookshelf(book: Book, bookshelf: Bookshelf)

    suspend fun deleteBookFromBookshelf(id: String, bookshelf: Bookshelf)
}