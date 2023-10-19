package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.entity.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.BookshelfRepository
import com.example.libraryapp.presentation.uistate.BookshelfListUiState
import com.example.libraryapp.presentation.uistate.BookshelfUIState
import kotlinx.coroutines.launch
import javax.inject.Inject


class BookshelfViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    private val _booksUIState = MutableLiveData<BookshelfUIState>(BookshelfUIState.Initializing)
    val booksUIState: LiveData<BookshelfUIState>
        get() = _booksUIState

    private val _bookshelfUIState =
        MutableLiveData<BookshelfListUiState>(BookshelfListUiState.Initializing)
    val bookshelfUIState: LiveData<BookshelfListUiState>
        get() = _bookshelfUIState

    fun getBooksFromBookshelf(bookshelf: Bookshelf) {
        _booksUIState.value = BookshelfUIState.Loading
        viewModelScope.launch {
            try {
                _booksUIState.value =
                    BookshelfUIState.Success(bookRepository.getAllBooksFromBookshelf(bookshelf))
            } catch (e: Exception) {
                _booksUIState.value =
                    BookshelfUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }

    fun getAllBookshelves() {
        _bookshelfUIState.value = BookshelfListUiState.Loading
        viewModelScope.launch {
            try {
                _bookshelfUIState.value =
                    BookshelfListUiState.Success(bookshelfRepository.getAllBookshelves())
            } catch (e: Exception) {
                _bookshelfUIState.value =
                    BookshelfListUiState.Error(e::class.toString(), e.message.toString())
            }
        }
    }

    fun createBookshelf(title: String) {
        viewModelScope.launch {
            try {
                bookshelfRepository.createBookshelf(title)
            } catch (e: Exception) {
                _bookshelfUIState.value =
                    BookshelfListUiState.Error(e::class.toString(), e.message.toString())
            }
        }
    }

    fun deleteBookshelf(bookshelf: Bookshelf) {
        viewModelScope.launch {
            try {
                bookshelfRepository.deleteBookshelf(bookshelf)
            } catch (e: Exception) {
                _bookshelfUIState.value =
                    BookshelfListUiState.Error(e::class.toString(), e.message.toString())
            }
        }
    }
}