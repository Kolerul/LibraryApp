package com.example.libraryapp.data.util

import com.example.libraryapp.data.db.enteties.BookDb
import com.example.libraryapp.domain.entity.Book

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
        bookshelf = bookshelf
    )
}

fun convertListBookToBookDb(list: List<Book>, bookshelf: String): List<BookDb> =
    list.map {  book ->
        convertBookToBookDb(book, bookshelf)
    }

fun convertBookDbToBook(book: BookDb): Book{
    val authors = book.authors.split(", ").toList()
    val categories = book.authors.split(", ").toList()

    return Book(
        title = book.title,
        authors = authors,
        publishedDate = book.publishedDate,
        publisher = book.publisher,
        imageUrl = book.imageUrl,
        description = book.description,
        categories = categories
    )
}

fun convertListBookDbToBook(list: List<BookDb>): List<Book> =
    list.map { book ->
        convertBookDbToBook(book)
    }