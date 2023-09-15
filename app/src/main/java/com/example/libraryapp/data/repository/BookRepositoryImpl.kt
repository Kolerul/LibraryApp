package com.example.libraryapp.data.repository

import com.example.libraryapp.data.network.api.BookApi
import com.example.libraryapp.data.util.convertListInetBookToBook
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val dispatcher: CoroutineDispatcher
): BookRepository {

    override suspend fun get(title: String, author: String): List<Book> = withContext(dispatcher){
        val part1 = title.ifBlank {
            ""
        }
        val part2 = if (author.isNotBlank()){
            "inauthor:$author"
        }else{
            ""
        }
        val request = part1 + part2
        val response = bookApi.getVolumes(request)
        return@withContext convertListInetBookToBook(response.items)
    }
}