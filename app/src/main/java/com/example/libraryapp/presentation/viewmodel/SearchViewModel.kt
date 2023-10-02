package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.presentation.uistate.SearchUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<SearchUIState>(SearchUIState.Initialization)
    val uiState: LiveData<SearchUIState>
        get() = _uiState

    fun searchBooksByTitleAndAuthor(title: String, author: String) {
        _uiState.value = SearchUIState.Loading
        viewModelScope.launch {
            try {
                val list = repository.getAllBooksByTitleAndAuthor(title, author)
                _uiState.value = SearchUIState.Success(list)
            } catch (e: Exception) {
                _uiState.value = SearchUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }

    fun addBookToBookshelf(book: Book, bookshelf: String) {
        viewModelScope.launch {
            try {
                repository.addBookToBookshelf(book, bookshelf)
            } catch (e: Exception) {
                _uiState.value = SearchUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }
}