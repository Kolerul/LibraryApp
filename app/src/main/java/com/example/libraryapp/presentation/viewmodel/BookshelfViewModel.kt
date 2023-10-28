package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.BookshelfRepository
import com.example.libraryapp.presentation.uistate.BookshelfListUiState
import com.example.libraryapp.presentation.uistate.BookshelfUIState
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

    private var _books: Flow<List<Book>> =
        bookRepository.getAllBooksFromBookshelf(_bookshelf.value!!)

    val books: LiveData<List<Book>>
        get() = _books.asLiveData()

    fun getBooksFromBookshelf(bookshelf: Bookshelf) {
        _bookshelf.value = bookshelf
    }

    fun getAllBookshelves(): Flow<List<Pair<Bookshelf, Int>>> =
        bookshelfRepository.getAllBookshelvesWithNumberOfBooks()

    fun createBookshelf(title: String) {
//        viewModelScope.launch {
//            try {
//                bookshelfRepository.createBookshelf(title)
//            } catch (e: Exception) {
//                _bookshelfUIState.value =
//                    BookshelfListUiState.Error(e::class.toString(), e.message.toString())
//            }
//        }
    }

    fun deleteBookshelf(bookshelf: Bookshelf) {
//        viewModelScope.launch {
//            try {
//                bookshelfRepository.deleteBookshelf(bookshelf)
//            } catch (e: Exception) {
//                _bookshelfUIState.value =
//                    BookshelfListUiState.Error(e::class.toString(), e.message.toString())
//            }
//        }
    }
}