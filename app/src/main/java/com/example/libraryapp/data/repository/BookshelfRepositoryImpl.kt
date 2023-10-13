package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.db.enteties.BookshelfDb
import com.example.libraryapp.data.util.convertBookshelfToBookshelfDb
import com.example.libraryapp.data.util.convertListBookshelfDbToBookshelf
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
        val bookshelf = BookshelfDb(bookshelfTitle = title)
        bookshelfDao.insertBookshelf(bookshelf)
    }

    override suspend fun deleteBookshelf(bookshelf: Bookshelf) = withContext(dispatcher) {
        bookshelfDao.deleteBookshelf(convertBookshelfToBookshelfDb(bookshelf))
    }

    override suspend fun getAllBookshelfs(): List<Bookshelf> = withContext(dispatcher) {
        val list = bookshelfDao.getAllBookshelfs()
        return@withContext convertListBookshelfDbToBookshelf(list)
    }
}