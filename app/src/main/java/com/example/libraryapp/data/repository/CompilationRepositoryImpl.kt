package com.example.libraryapp.data.repository

import com.example.libraryapp.data.network.api.BookApi
import com.example.libraryapp.data.util.convertListInetBookToBook
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.PrintTypes
import com.example.libraryapp.domain.repository.CompilationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompilationRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val dispatcher: CoroutineDispatcher
) : CompilationRepository {

    override suspend fun getBooksByCategory(
        category: String,
        startIndex: Int,
        isNewest: Boolean,
        printType: PrintTypes
    ): List<Book> = withContext(dispatcher) {
        val order = if (isNewest) {
            "newest"
        } else {
            "relevance"
        }

        val type = when (printType) {
            PrintTypes.BOOKS -> "books"
            PrintTypes.MAGAZINES -> "magazines"
            PrintTypes.ALL -> "all"
        }

        val response = bookApi.getVolumes(
            intelligence = category,
            category = category,
            startIndex = startIndex,
            printType = type,
            orderBy = order,
            apiKey = BookRepositoryImpl.API_KEY
        )

        return@withContext convertListInetBookToBook(response.items)
    }

    override suspend fun getCategories(): List<String> = withContext(dispatcher) {
        listOf(
            "Fiction",
            "Poetry",
            "Comics & Graphic Novels",
            "Juvenile Fiction",
            "Literary Criticism",
            "Music",
            "Science",
            "Foreign Language Study",
            "Education",
            "Detective",
            "Adventure"
        )
    }

}
