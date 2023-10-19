package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.entity.Bookshelf

sealed class BookshelfListUiState {
    data object Initializing : BookshelfListUiState()
    data object Loading : BookshelfListUiState()
    data class Success(val bookshelves: List<Bookshelf>) : BookshelfListUiState()
    data class Error(val errorType: String, val message: String) : BookshelfListUiState()
}