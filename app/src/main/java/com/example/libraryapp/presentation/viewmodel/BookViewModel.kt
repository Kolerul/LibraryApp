package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book>
        get() = _book


    fun selectBook(book: Book) {
        _book.value = book
    }

    fun addBookToBookshelf(book: Book, bookshelf: String) {
        viewModelScope.launch {
            repository.addBookToBookshelf(book, Bookshelf(bookshelf))
        }
    }
}