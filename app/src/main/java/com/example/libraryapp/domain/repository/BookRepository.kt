package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.entity.Book

interface BookRepository {
    suspend fun get(title: String, author: String): List<Book>
}