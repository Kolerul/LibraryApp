package com.example.libraryapp.data.util

import com.example.libraryapp.data.db.model.BookDb
import com.example.libraryapp.data.db.model.BookshelfDb
import com.example.libraryapp.data.db.model.BookshelfWithBooks
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf

fun convertBookToBookDb(book: Book, bookshelf: String): BookDb{
    val authors = book.authors.joinToString(
        separator = ", "
    )
    val categories = book.categories.joinToString(
        separator = ", "
    )

    return BookDb(
        title = book.title,
        authors = authors,
        description = book.description,
        publishedDate = book.publishedDate,
        publisher = book.publisher,
        imageUrl = book.imageUrl,
        categories = categories,
        bookshelf = bookshelf,
        bookId = book.id
    )
}

fun convertListBookToBookDb(list: List<Book>, bookshelf: String): List<BookDb> =
    list.map {  book ->
        convertBookToBookDb(book, bookshelf)
    }

fun convertBookDbToBook(book: BookDb): Book{
    val authors = book.authors.split(", ").toList()
    val categories = book.categories.split(", ").toList()

    return Book(
        title = book.title,
        authors = authors,
        publishedDate = book.publishedDate,
        publisher = book.publisher,
        imageUrl = book.imageUrl,
        description = book.description,
        categories = categories,
        id = book.bookId
    )
}

fun convertBookshelfWithBooksToPair(bookshelf: BookshelfWithBooks): Pair<Bookshelf, Int> =
    Pair(convertToBookshelf(bookshelf.bookshelf), bookshelf.bookList.size)

fun convertListBookshelfWithBooksToPair(list: List<BookshelfWithBooks>): List<Pair<Bookshelf, Int>> =
    list.map { bookshelf ->
        convertBookshelfWithBooksToPair(bookshelf)
    }

fun convertListBookDbToBook(list: List<BookDb>): List<Book> =
    list.map { book ->
        convertBookDbToBook(book)
    }

fun convertToBookshelfDb(bookshelf: Bookshelf) =
    BookshelfDb(
        bookshelfTitle = bookshelf.bookshelfTitle
    )

fun convertToBookshelf(bookshelf: BookshelfDb) =
    Bookshelf(
        bookshelfTitle = bookshelf.bookshelfTitle
    )

fun convertListToBookshelf(list: List<BookshelfDb>): List<Bookshelf> =
    list.map { bookshelf ->
        convertToBookshelf(bookshelf)
    }