package com.example.libraryapp.data.util

import com.example.libraryapp.data.network.model.InetBook
import com.example.libraryapp.domain.entity.Book

fun convertInetBookToBook( inetBook: InetBook): Book =
    Book(
        title = inetBook.volumeInfo.title,
        authors = inetBook.volumeInfo.authors ?: listOf("Unknown"),
        publishedDate = inetBook.volumeInfo.publishedDate ?: "Unknown",
        description = inetBook.volumeInfo.description ?: "None",
        publisher = inetBook.volumeInfo.publisher,
        imageUrl = inetBook.volumeInfo.imageLinks.thumbnail,
        categories = inetBook.volumeInfo.categories ?: emptyList()
    )

fun convertListInetBookToBook(list: List<InetBook>): List<Book> =
    list.map { inetBook ->
        convertInetBookToBook(inetBook)
    }


