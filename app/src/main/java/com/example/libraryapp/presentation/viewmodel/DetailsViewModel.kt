package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.presentation.uistate.DetailsUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DetailsUIState>(DetailsUIState.Initialization)
    val uiState: LiveData<DetailsUIState>
        get() = _uiState

    fun getBook(id: String, bookshelf: String) {
        _uiState.value = DetailsUIState.Loading
        viewModelScope.launch {
            try {
                val book = repository.getBookById(id, bookshelf)
                _uiState.value = DetailsUIState.Success(book)
            } catch (e: Exception) {
                _uiState.value = DetailsUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteBookFromBookshelf(id)
            } catch (e: Exception) {
                _uiState.value = DetailsUIState.Error(e::class.toString(), e.message.toString())
            }
        }
    }
}