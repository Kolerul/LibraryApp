package com.example.libraryapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.presentation.uistate.BookshelfUIState
import kotlinx.coroutines.launch
import javax.inject.Inject


class BookshelfViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<BookshelfUIState>()
    val uiState: LiveData<BookshelfUIState>
        get() = _uiState

    fun getBooksFromBookshelf(bookshelf: String) {
        _uiState.value = BookshelfUIState.Loading
        viewModelScope.launch {
            try {
                _uiState.value =
                    BookshelfUIState.Success(repository.getAllBooksFromBookshelf(bookshelf))
                Log.d("BookshelfFragment", _uiState.value.toString())
            } catch (e: Exception) {
                _uiState.value = BookshelfUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }
}