package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.entity.Book

sealed class BookshelfUIState {
    object Initializing : BookshelfUIState()
    object Loading : BookshelfUIState()
    data class Success(val books: List<Book>) : BookshelfUIState()
    data class Error(val errorType: String, val message: String) : BookshelfUIState()
}