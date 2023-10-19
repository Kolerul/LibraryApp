package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.network.api.BookApi
import com.example.libraryapp.data.util.convertBookDbToBook
import com.example.libraryapp.data.util.convertBookToBookDb
import com.example.libraryapp.data.util.convertListBookDbToBook
import com.example.libraryapp.data.util.convertListInetBookToBook
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.domain.entity.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val dispatcher: CoroutineDispatcher
): BookRepository {

    override suspend fun getAllBooksByTitleAndAuthor(title: String, author: String): List<Book> = withContext(dispatcher){
        val part1 = title.ifBlank {
            ""
        }
        val part2 = if (author.isNotBlank()) {
            "inauthor:$author"
        } else {
            ""
        }
        val request = part1 + part2
        val response = bookApi.getVolumes(request)
        return@withContext convertListInetBookToBook(response.items)
    }

    override suspend fun getAllBooksFromBookshelf(bookshelf: Bookshelf): List<Book> =
        withContext(dispatcher) {
            val booksDb = bookDao.getAllBooksFromBookshelf(bookshelf.bookshelfTitle)
            return@withContext convertListBookDbToBook(booksDb)
        }

    override suspend fun getBookById(id: String, bookshelf: Bookshelf): Book =
        withContext(dispatcher) {
            val bookDb = bookDao.getBook(id)
            return@withContext convertBookDbToBook(bookDb)
        }

    override suspend fun addBookToBookshelf(book: Book, bookshelf: Bookshelf): Unit =
        withContext(dispatcher) {
            val bookDb = convertBookToBookDb(book, bookshelf.bookshelfTitle)
            bookDao.addBook(bookDb)
        }

    override suspend fun deleteBookFromBookshelf(id: String, bookshelf: Bookshelf): Unit =
        withContext(dispatcher) {
            bookDao.deleteBookFromBookshelfById(id, bookshelf.bookshelfTitle)
        }
}