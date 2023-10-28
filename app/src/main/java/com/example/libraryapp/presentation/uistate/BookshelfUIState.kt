package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.model.Book

sealed class BookshelfUIState {
    data object Initializing : BookshelfUIState()
    data object Loading : BookshelfUIState()
    data class Success(val books: List<Book>) : BookshelfUIState()
    data class Error(val errorType: String, val message: String) : BookshelfUIState()
}