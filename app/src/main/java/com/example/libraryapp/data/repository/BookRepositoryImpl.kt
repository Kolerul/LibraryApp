package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.network.api.BookApi
import com.example.libraryapp.data.util.convertBookDbToBook
import com.example.libraryapp.data.util.convertBookToBookDb
import com.example.libraryapp.data.util.convertListBookDbToBook
import com.example.libraryapp.data.util.convertListInetBookToBook
import com.example.libraryapp.domain.entity.Book
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
        val part2 = if (author.isNotBlank()){
            "inauthor:$author"
        }else{
            ""
        }
        val request = part1 + part2
        val response = bookApi.getVolumes(request)
        return@withContext convertListInetBookToBook(response.items)
    }

    override suspend fun getAllBooksFromBookshelf(bookshelf: String): List<Book> = withContext(dispatcher){
        val booksDb = bookDao.getAllBooksFromBookshelf(bookshelf)
        return@withContext convertListBookDbToBook(booksDb)
    }

    override suspend fun getBookByTitleFromBookshelf(title: String, bookshelf: String): Book = withContext(dispatcher){
        val bookDb = bookDao.getBookFromBookshelfByTitle(title, bookshelf)
        return@withContext convertBookDbToBook(bookDb)
    }

    override suspend fun addBookToBookshelf(book: Book, bookshelf: String): Unit = withContext(dispatcher) {
        val bookDb = convertBookToBookDb(book, bookshelf)
        bookDao.addBook(bookDb)
    }

    override suspend fun deleteBookFromBookshelf(book: Book, bookshelf: String): Unit = withContext(dispatcher) {
        val bookDb = convertBookToBookDb(book, bookshelf)
        bookDao.deleteBook(bookDb)
    }
}