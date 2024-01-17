package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.PrintTypes


interface CompilationRepository {

    suspend fun getBooksByCategory(
        category: String,
        startIndex: Int,
        isNewest: Boolean,
        printType: PrintTypes
    ): List<Book>

    suspend fun getCategories(): List<String>
}