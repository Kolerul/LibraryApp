package com.example.libraryapp.data.repository

import com.example.libraryapp.data.db.dao.BookDao
import com.example.libraryapp.data.db.dao.BookshelfDao
import com.example.libraryapp.data.network.api.BookApi
import com.example.libraryapp.data.util.convertBookDbToBook
import com.example.libraryapp.data.util.convertBookToBookDb
import com.example.libraryapp.data.util.convertListBookDbToBook
import com.example.libraryapp.data.util.convertListInetBookToBook
import com.example.libraryapp.data.util.convertToBookshelfDb
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val bookshelfDao: BookshelfDao,
    private val dispatcher: CoroutineDispatcher
): BookRepository {

    override suspend fun getAllBooksByTitleAndAuthor(title: String, author: String): List<Book> = withContext(dispatcher){
        val request = if (title.isNotBlank() && author.isNotBlank())
            "$title+inauthor:$author"
        else if (title.isBlank())
            "inauthor:$author"
        else
            title
        val response = bookApi.getVolumes(
            intelligence = request,
            title = title,
            author = author,
            apiKey = API_KEY
        )
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
            val bookshelves = bookshelfDao.getAllBookshelves()
            val bookshelvesTitles = bookshelves.map { bookshelfDb ->
                bookshelfDb.bookshelfTitle
            }
            if (!bookshelvesTitles.contains(bookshelf.bookshelfTitle)) {
                bookshelfDao.insertBookshelf(convertToBookshelfDb(bookshelf))
            }
            val bookDb = convertBookToBookDb(book, bookshelf.bookshelfTitle)
            bookDao.addBook(bookDb)
        }

    override suspend fun deleteBookFromBookshelf(id: String, bookshelf: Bookshelf): Unit =
        withContext(dispatcher) {
            bookDao.deleteBookFromBookshelfById(id, bookshelf.bookshelfTitle)
        }

    companion object {
        const val API_KEY = "AIzaSyAtUuma6RluGjpPfNZ8sC1y38mieCiVMg0"
    }
}