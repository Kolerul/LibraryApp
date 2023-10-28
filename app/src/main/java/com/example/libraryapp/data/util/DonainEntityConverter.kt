package com.example.libraryapp.data.util

import com.example.libraryapp.data.network.model.InetBook
import com.example.libraryapp.domain.model.Book

fun convertInetBookToBook(inetBook: InetBook): Book {
    return Book(
        title = inetBook.volumeInfo.title ?: "None",
        authors = inetBook.volumeInfo.authors ?: listOf("Unknown"),
        publishedDate = inetBook.volumeInfo.publishedDate ?: "Unknown",
        description = inetBook.volumeInfo.description ?: "None",
        publisher = inetBook.volumeInfo.publisher ?: "None",
        imageUrl =
        inetBook.volumeInfo.imageLinks?.thumbnail ?: inetBook.volumeInfo.imageLinks?.smallThumbnail
        ?: "",
        categories = inetBook.volumeInfo.categories ?: emptyList(),
        id = inetBook.id
    )
}


fun convertListInetBookToBook(list: List<InetBook>): List<Book> =
    list.map { inetBook ->
        convertInetBookToBook(inetBook)
    }


