package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.util.convertListBookshelfWithBooksToPair
import com.example.libraryapp.data.util.convertListToBookshelf
import com.example.libraryapp.data.util.convertToBookshelfDb
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookshelfRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookshelfRepositoryImpl @Inject constructor(
    private val bookshelfDao: BookshelfDao,
    private val bookDao: BookDao,
    private val dispatcher: CoroutineDispatcher
) : BookshelfRepository {

    override suspend fun createBookshelf(title: String) = withContext(dispatcher) {
        val bookshelf = Bookshelf(title)
        bookshelfDao.insertBookshelf(convertToBookshelfDb(bookshelf))
    }

    override suspend fun deleteBookshelf(bookshelf: Bookshelf) = withContext(dispatcher) {
        if (bookshelf.bookshelfTitle != "Favourite") {
            bookDao.deleteAllBooksFromBookshelf(bookshelf.bookshelfTitle)
            bookshelfDao.deleteBookshelf(convertToBookshelfDb(bookshelf))
        }
    }

    override suspend fun getAllBookshelves(): List<Bookshelf> = withContext(dispatcher) {
        val response = bookshelfDao.getAllBookshelves()
        return@withContext convertListToBookshelf(response)
    }

    override fun getAllBookshelvesWithNumberOfBooks(): Flow<List<Pair<Bookshelf, Int>>> {
        val response = bookshelfDao.getAllBookshelvesWithBooks()
        val listOfPairs = response.map { list ->
            convertListBookshelfWithBooksToPair(list)
        }
        return listOfPairs
    }
}