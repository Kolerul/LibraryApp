package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.entity.Book

sealed class DetailsUIState {
    data object Initialization : DetailsUIState()
    data object Loading : DetailsUIState()
    data class Success(val book: Book) : DetailsUIState()
    data class Error(val errorType: String, val message: String) : DetailsUIState()
}