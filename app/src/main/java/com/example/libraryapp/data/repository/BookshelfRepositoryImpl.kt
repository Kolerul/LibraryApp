package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.util.convertListBookshelfWithBooksToPair
import com.example.libraryapp.data.util.convertListToBookshelf
import com.example.libraryapp.data.util.convertToBookshelfDb
import com.example.libraryapp.domain.entity.Bookshelf
import com.example.libraryapp.domain.repository.BookshelfRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookshelfRepositoryImpl @Inject constructor(
    private val bookshelfDao: BookshelfDao,
    private val dispatcher: CoroutineDispatcher
) : BookshelfRepository {

    override suspend fun createBookshelf(title: String) = withContext(dispatcher) {
        val bookshelf = Bookshelf(title)
        bookshelfDao.insertBookshelf(convertToBookshelfDb(bookshelf))
    }

    override suspend fun deleteBookshelf(bookshelf: Bookshelf) = withContext(dispatcher) {
        bookshelfDao.deleteBookshelf(convertToBookshelfDb(bookshelf))
    }

    override suspend fun getAllBookshelves(): List<Bookshelf> = withContext(dispatcher) {
        val response = bookshelfDao.getAllBookshelves()
        return@withContext convertListToBookshelf(response)
    }

    override suspend fun getAllBookshelvesWithNumberOfBooks(): List<Pair<Bookshelf, Int>> =
        withContext(dispatcher) {
            val response = bookshelfDao.getAllBookshelvesWithBooks()
            return@withContext convertListBookshelfWithBooksToPair(response)
        }
}