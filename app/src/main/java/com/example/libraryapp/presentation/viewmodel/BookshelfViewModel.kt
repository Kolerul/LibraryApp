package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.BookshelfRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class BookshelfViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    private var _bookshelf = MutableLiveData(Bookshelf("Favourite"))
    val bookshelf: LiveData<Bookshelf>
        get() = _bookshelf

    private var _books = MutableLiveData<List<Book>>(emptyList())

    val books: LiveData<List<Book>>
        get() = _books

    fun getBooksFromBookshelf(bookshelf: Bookshelf) {
        viewModelScope.launch {
            _bookshelf.value = bookshelf
            _books.value = bookRepository.getAllBooksFromBookshelf(_bookshelf.value!!)
        }
    }

    fun getAllBookshelves(): Flow<List<Pair<Bookshelf, Int>>> {
        val flow = bookshelfRepository.getAllBookshelvesWithNumberOfBooks()
        viewModelScope.launch {
            flow.collect { list ->
                if (list.isNotEmpty()) {
                    getBooksFromBookshelf(list[0].first)
                }
            }
        }
        return flow
    }


    fun createBookshelf(title: String) {
        viewModelScope.launch {
            bookshelfRepository.createBookshelf(title)
        }
    }

    fun deleteBookshelf(bookshelf: Bookshelf) {
        viewModelScope.launch {
            bookshelfRepository.deleteBookshelf(bookshelf)
        }
    }
}